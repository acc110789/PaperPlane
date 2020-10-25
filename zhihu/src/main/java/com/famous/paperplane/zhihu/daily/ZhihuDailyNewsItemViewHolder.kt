package com.famous.paperplane.zhihu.daily

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.imageService
import com.famous.paperplane.business_base.layoutInflater
import com.famous.paperplane.zhihu.R
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import kotlinx.android.extensions.LayoutContainer

interface ZhihuDailyNewsItemContext {
    fun onItemClick(item: ZhihuDailyNewsQuestion)
}

class ZhihuDailyNewsItemViewHolder(
    itemView: View,
    private val itemContext: ZhihuDailyNewsItemContext
) : RecyclerView.ViewHolder(itemView), View.OnClickListener, LayoutContainer {

    companion object {
        fun create(
            parent: ViewGroup,
            context: ZhihuDailyNewsItemContext
        ): ZhihuDailyNewsItemViewHolder {
            val itemView =
                parent.layoutInflater().inflate(R.layout.item_universal_layout, parent, false)
            return ZhihuDailyNewsItemViewHolder(
                itemView,
                context
            )
        }
    }

    private val cover: ImageView = itemView.findViewById(R.id.image_view_cover)
    private val title: TextView = itemView.findViewById(R.id.text_view_title)

    private var item: ZhihuDailyNewsQuestion? = null

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

    override val containerView: View? get() = itemView

}