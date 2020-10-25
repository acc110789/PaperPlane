package com.famous.paperplane.zhihu.daily

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.imageService
import com.famous.paperplane.zhihu.R
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion

class ZhihuDailyNewsItemViewHolder(
    itemView: View,
    private val itemContext: ZhihuDailyNewsItemContext
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var item: ZhihuDailyNewsQuestion? = null

    private val cover: ImageView = itemView.findViewById(R.id.image_view_cover)
    private val title: TextView = itemView.findViewById(R.id.text_view_title)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: ZhihuDailyNewsQuestion) {
        this.item = item
        imageService.loadImage(cover, item.images?.get(0))
        title.text = item.title
    }

    override fun onClick(view: View) {
        val item = this.item ?: return
        itemContext.onItemClick(item)
    }
}