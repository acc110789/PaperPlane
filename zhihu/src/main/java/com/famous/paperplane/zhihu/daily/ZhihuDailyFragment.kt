/*
 * Copyright 2016 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.famous.paperplane.zhihu.daily

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.*
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import com.famous.paperplane.zhihu.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_timeline_page.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Main UI for the zhihu daily news screen.
 * Displays a grid of [ZhihuDailyNewsQuestion]s.
 */

class ZhihuDailyFragment : androidx.fragment.app.Fragment(), ZhihuDailyNewsItemContext {

    private val viewModel: ZhihuDailyViewModel by inject()
    private val mAdapter: ZhihuDailyNewsAdapter by inject { parametersOf(this) }

    private lateinit var mLayoutManager: LinearLayoutManager

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    private var mIsFirstLoad = true

    companion object {
        fun newInstance(): ZhihuDailyFragment = ZhihuDailyFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+08")
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_timeline_page, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRefreshLayout()

        initRecyclerView()

        viewModel.newsList.observe(viewLifecycleOwner) {
            showResult(it)
        }
        viewModel.showLoadingIndicator.observe(viewLifecycleOwner) {
            refresh_layout.post {
                refresh_layout.isRefreshing = it
            }
        }
    }

    private fun initRefreshLayout() {
        val context = this.context ?: return
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        refresh_layout.setOnRefreshListener {
            val c = Calendar.getInstance()
            c.timeZone = TimeZone.getTimeZone("GMT+08")
            viewModel.loadNews(true, c.timeInMillis)
        }
    }

    private fun initRecyclerView() {
        recycler_view.adapter = mAdapter
        mLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    activity?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.itemCount - 1) {
                        loadMore()
                    }
                } else {
                    activity?.findViewById<FloatingActionButton>(R.id.fab)?.show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+08")
        c.set(mYear, mMonth, mDay)

        if (mIsFirstLoad) {
            viewModel.loadNews( false, c.timeInMillis)
            mIsFirstLoad = false
        } else {
            viewModel.loadNewsFromCache()
        }
    }

    private fun showResult(list: List<ZhihuDailyNewsQuestion>) {
        mAdapter.updateData(list)

        empty_view.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        recycler_view.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

        // Cache data of the items
        for ((_, _, id) in list) {
            val intent = Intent(CacheServiceParam.BROADCAST_FILTER_ACTION)
            intent.putExtra(CacheServiceParam.FLAG_ID, id)
            intent.putExtra(CacheServiceParam.FLAG_TYPE, PostType.ZHIHU)
            context?.let {
                LocalBroadcastManager.getInstance(it).sendBroadcast(intent)
            }
        }
    }

    private fun loadMore() {
        val c = Calendar.getInstance()
        c.set(mYear, mMonth, --mDay)
        viewModel.loadNews(false, c.timeInMillis)
    }

    fun showDatePickerDialog() {
        val activity = activity ?: return

        val c = Calendar.getInstance()
        c.set(mYear, mMonth, mDay)
        val dialog = DatePickerDialog.newInstance({ _, year, monthOfYear, dayOfMonth ->
            mYear = year
            mMonth = monthOfYear
            mDay = dayOfMonth
            c.set(mYear, monthOfYear, mDay)

            viewModel.loadNews(true, c.timeInMillis)

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        dialog.maxDate = Calendar.getInstance()

        val minDate = Calendar.getInstance()
        minDate.set(2013, 5, 20)
        dialog.minDate = minDate
        dialog.vibrate(false)

        dialog.show(activity.fragmentManager, ZhihuDailyFragment::class.java.simpleName)
    }
}
