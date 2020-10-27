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

package com.famous.paperplane.douban

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.*
import com.famous.paperplane.douban.entity.DoubanMomentNewsPosts
import com.famous.paperplane.business_base.app.appModule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_timeline_page.view.*
import java.util.*

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Main UI for the douban moment news.
 * Displays a grid of [DoubanMomentNewsPosts].
 */

class DoubanMomentFragment : androidx.fragment.app.Fragment(), DoubanMomentContract.View {

    override lateinit var mPresenter: DoubanMomentContract.Presenter

    private var mAdapter: DoubanMomentNewsAdapter? = null
    private lateinit var mLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    private var mIsFirstLoad = true
    private var mListSize = 0

    override val isActive: Boolean = isAdded && isResumed

    companion object {

        fun newInstance(): DoubanMomentFragment = DoubanMomentFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mYear = Calendar.getInstance().get(Calendar.YEAR)
        mMonth = Calendar.getInstance().get(Calendar.MONTH)
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_timeline_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            view.refresh_layout.setColorSchemeColors(ContextCompat.getColor(it, R.color.colorAccent))
        }
        view.refresh_layout.setOnRefreshListener {
            val c = Calendar.getInstance()
            c.timeZone = TimeZone.getTimeZone("GMT+08")
            mPresenter.load(true, true, c.timeInMillis)
        }

        mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        view.recycler_view.layoutManager = mLayoutManager
        view.recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mListSize - 1) {
                        loadMore()
                    }
                    activity?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
                } else {
                    activity?.findViewById<FloatingActionButton>(R.id.fab)?.show()
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
        val c = Calendar.getInstance()
        c.set(mYear, mMonth, mDay)
        setLoadingIndicator(mIsFirstLoad)
        if (mIsFirstLoad) {
            mPresenter.load(true, false, c.timeInMillis)
            mIsFirstLoad = false
        } else {
            mPresenter.load(false, false, c.timeInMillis)
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        view?.refresh_layout?.post {
            view?.refresh_layout?.isRefreshing = active
        }
    }

    override fun showResult(list: MutableList<DoubanMomentNewsPosts>) {
        if (mAdapter == null) {
            mAdapter = DoubanMomentNewsAdapter(list)
            mAdapter?.setItemClickListener(object : OnRecyclerViewItemOnClickListener {
                override fun onItemClick(v: View, position: Int) {
                    val intent = Intent(activity, appModule.detailActivityClass()).apply {
                        putExtra(DetailActivityParam.KEY_ARTICLE_ID, list[position].id)
                        putExtra(DetailActivityParam.KEY_ARTICLE_TYPE, ContentType.TYPE_DOUBAN_MOMENT)
                        putExtra(DetailActivityParam.KEY_ARTICLE_TITLE, list[position].title)
                        putExtra(DetailActivityParam.KEY_ARTICLE_IS_FAVORITE, list[position].isFavorite)
                    }
                    startActivity(intent)
                }
            })
            mAdapter
            view?.recycler_view?.adapter = mAdapter
        } else {
            mAdapter?.updateData(list)
        }

        mListSize = list.size

        view?.empty_view?.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        view?.recycler_view?.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

        for ((_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, id) in list) {
            val intent = Intent(CacheServiceParam.BROADCAST_FILTER_ACTION)
            intent.putExtra(CacheServiceParam.FLAG_ID, id)
            intent.putExtra(CacheServiceParam.FLAG_TYPE, PostType.DOUBAN)
            context?.let {
                androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(it).sendBroadcast(intent)
            }
        }
    }

    private fun loadMore() {
        val c = Calendar.getInstance()
        c.set(mYear, mMonth, --mDay)
        mPresenter.load(true, false, c.timeInMillis)
    }

    fun showDatePickerDialog() {
        val dialog = DatePickerDialog.newInstance({ _, year, monthOfYear, dayOfMonth ->
            mYear = year
            mMonth = monthOfYear
            mDay = dayOfMonth
            val c = Calendar.getInstance()
            c.set(mYear, mMonth, mDay)
            mPresenter.load(true, true, c.timeInMillis)

        }, mYear, mMonth, mDay)

        dialog.maxDate = Calendar.getInstance()

        val minDate = Calendar.getInstance()
        minDate.set(2014, 5, 12)
        dialog.minDate = minDate

        dialog.vibrate(false)
        dialog.show(activity?.fragmentManager, DoubanMomentFragment::class.java.simpleName)

    }

}
