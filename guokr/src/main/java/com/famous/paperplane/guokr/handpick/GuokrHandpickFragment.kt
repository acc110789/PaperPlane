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

package com.famous.paperplane.guokr.handpick

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.famous.paperplane.business_base.*
import com.famous.paperplane.business_base.app.appModule
import com.famous.paperplane.guokr.entity.GuokrHandpickNewsResult

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Main UI for the guokr handpick news.
 * Displays a grid of [GuokrHandpickNewsResult]s.
 */

class GuokrHandpickFragment : androidx.fragment.app.Fragment(), GuokrHandpickContract.View {

    override lateinit var mPresenter: GuokrHandpickContract.Presenter

    private var mAdapter: GuokrHandpickNewsAdapter? = null
    private lateinit var mLayoutManager: androidx.recyclerview.widget.LinearLayoutManager

    private var mOffset = 0
    private var mIsFirstLoad = true
    private var mListSize = 0

    override val isActive: Boolean
        get() = isAdded && isResumed

    companion object {
        fun newInstance(): GuokrHandpickFragment = GuokrHandpickFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_timeline_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            view.refresh_layout.setColorSchemeColors(ContextCompat.getColor(it, R.color.colorAccent))
        }
        view.refresh_layout.setOnRefreshListener {
            mPresenter.load(true, true, 0, 20)
            mOffset = 0
        }

        mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        view.recycler_view.layoutManager = mLayoutManager
        view.recycler_view.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && mLayoutManager.findLastCompletelyVisibleItemPosition() == mListSize - 1) {
                    loadMore()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
        setLoadingIndicator(mIsFirstLoad)
        if (mIsFirstLoad) {
            mPresenter.load(true, false, mOffset, 20)
            mIsFirstLoad = false
        } else {
            mPresenter.load(false, false, mOffset, 20)
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        view?.refresh_layout?.post {
            view?.refresh_layout?.isRefreshing = active
        }
    }

    override fun showResult(list: MutableList<GuokrHandpickNewsResult>) {
        mOffset = list.size
        if (mAdapter == null) {
            mAdapter = GuokrHandpickNewsAdapter(list)
            mAdapter?.setItemClickListener(object : OnRecyclerViewItemOnClickListener {

                override fun onItemClick(v: View, position: Int) {
                    val intent = Intent(activity, appModule.detailActivityClass()).apply {
                        putExtra(DetailActivityParam.KEY_ARTICLE_ID, list[position].id)
                        putExtra(DetailActivityParam.KEY_ARTICLE_TYPE, ContentType.TYPE_GUOKR_HANDPICK)
                        putExtra(DetailActivityParam.KEY_ARTICLE_TITLE, list[position].title)
                        putExtra(DetailActivityParam.KEY_ARTICLE_IS_FAVORITE, list[position].isFavorite)
                    }
                    startActivity(intent)
                }

            })
            view?.recycler_view?.adapter = mAdapter
        } else {
            mAdapter?.updateData(list)
        }

        mListSize = list.size

        view?.empty_view?.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        view?.recycler_view?.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE

        for ((_, _, _, _, _, id) in list) {
            val intent = Intent(CacheServiceParam.BROADCAST_FILTER_ACTION)
            intent.putExtra(CacheServiceParam.FLAG_ID, id)
            intent.putExtra(CacheServiceParam.FLAG_TYPE, PostType.GUOKR)
            context?.let {
                LocalBroadcastManager.getInstance(it).sendBroadcast(intent)
            }
        }

    }

    private fun loadMore() {
        mPresenter.load(true, false, mOffset, 20)
    }

}