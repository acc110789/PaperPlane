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

package com.marktony.zhihudaily.timeline

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marktony.zhihudaily.R
import com.famous.paperplane.douban.entity.DoubanMomentNewsPosts
import com.marktony.zhihudaily.glide.loadImage
import com.famous.paperplane.business_base.OnRecyclerViewItemOnClickListener
import com.famous.paperplane.business_base.image_view_cover
import com.famous.paperplane.business_base.text_view_title

/**
 * Created by lizhaotailang on 2017/5/22.
 *
 * Adapter between the data of [DoubanMomentNewsPosts] and [RecyclerView].
 */

class DoubanMomentNewsAdapter(private val mList: MutableList<DoubanMomentNewsPosts>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private var mListener: OnRecyclerViewItemOnClickListener? = null

    companion object {
        private const val TYPE_ITEM = 0x00
        private const val TYPE_NO_IMG = 0x01
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return if (i == TYPE_ITEM) {
            ItemViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_universal_layout, viewGroup, false), mListener)
        } else {
            NoImgViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_universal_without_image, viewGroup, false), mListener)
        }
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, i: Int) {
        val (_, _, _, _, _, _, _, _, _, _, _, _, _, thumbs, _, title) = mList[i]
        if (viewHolder is ItemViewHolder) {
            with(viewHolder.itemView) {
                image_view_cover.loadImage(thumbs[0].medium.url)
                text_view_title.text = title
            }
        } else if (viewHolder is NoImgViewHolder) {
            viewHolder.itemView.text_view_title.text = title
        }
    }

    override fun getItemCount(): Int = mList.size

    override fun getItemViewType(position: Int): Int = if (mList[position].thumbs.isEmpty()) TYPE_NO_IMG else TYPE_ITEM

    fun updateData(list: List<DoubanMomentNewsPosts>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
        notifyItemRemoved(list.size)
    }

    fun setItemClickListener(listener: OnRecyclerViewItemOnClickListener) {
        this.mListener = listener
    }

    class ItemViewHolder(
            itemView: View,
            var listener: OnRecyclerViewItemOnClickListener?
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener?.onItemClick(view, layoutPosition)
        }
    }

    class NoImgViewHolder(
            itemView: View,
            private val listener: OnRecyclerViewItemOnClickListener?
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener?.onItemClick(view, layoutPosition)
        }
    }

}
