package com.famous.paperplane.zhihu

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.OnRecyclerViewItemOnClickListener
import com.famous.paperplane.business_base.imageService
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion

class ZhihuDailyNewsItemVIewHolder(
    itemView: View,
    private val listener: OnRecyclerViewItemOnClickListener?
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val cover: ImageView = itemView.findViewById(R.id.image_view_cover)
    private val title: TextView = itemView.findViewById(R.id.text_view_title)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: ZhihuDailyNewsQuestion) {
        imageService.loadImage(cover, item.images?.get(0))
        this.title.text = item.title
    }

    override fun onClick(view: View) {
        listener?.onItemClick(view, layoutPosition)
    }

}