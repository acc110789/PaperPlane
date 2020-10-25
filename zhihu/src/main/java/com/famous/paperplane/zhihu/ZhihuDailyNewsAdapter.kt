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

package com.famous.paperplane.zhihu

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.OnRecyclerViewItemOnClickListener
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Adapter between the data of [ZhihuDailyNewsQuestion] and [RecyclerView].
 */

class ZhihuDailyNewsAdapter(private val itemContext: ZhihuDailyNewsItemContext) : RecyclerView.Adapter<ZhihuDailyNewsItemViewHolder>() {

    private val mList = mutableListOf<ZhihuDailyNewsQuestion>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ZhihuDailyNewsItemViewHolder.create(viewGroup, context = itemContext)

    override fun onBindViewHolder(viewHolder: ZhihuDailyNewsItemViewHolder, i: Int) = viewHolder.bind(mList[i])

    override fun getItemCount(): Int = mList.size

    fun updateData(list: List<ZhihuDailyNewsQuestion>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }


}
