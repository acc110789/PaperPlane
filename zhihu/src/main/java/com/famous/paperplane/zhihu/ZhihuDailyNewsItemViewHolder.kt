package com.famous.paperplane.zhihu

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.OnRecyclerViewItemOnClickListener
import com.famous.paperplane.business_base.imageService
import com.famous.paperplane.business_base.layoutInflater
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import kotlinx.android.extensions.LayoutContainer

class ZhihuDailyNewsItemViewHolder(
    itemView: View,
    private val listener: OnRecyclerViewItemOnClickListener?
) : RecyclerView.ViewHolder(itemView), View.OnClickListener, LayoutContainer {

    companion object {
        fun create(parent: ViewGroup, listener: OnRecyclerViewItemOnClickListener?): ZhihuDailyNewsItemViewHolder {
            val itemView = parent.layoutInflater().inflate(R.layout.item_universal_layout, parent, false)
            return ZhihuDailyNewsItemViewHolder(itemView, listener)
        }
    }

    private val cover: ImageView = itemView.findViewById(R.id.image_view_cover)
    private val title: TextView = itemView.findViewById(R.id.text_view_title)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: ZhihuDailyNewsQuestion) {
        imageService.loadImage(cover, item.images?.get(0))
        title.text = item.title
    }

    override fun onClick(view: View) {
        listener?.onItemClick(view, layoutPosition)
    }

    override val containerView: View? get() = itemView

}