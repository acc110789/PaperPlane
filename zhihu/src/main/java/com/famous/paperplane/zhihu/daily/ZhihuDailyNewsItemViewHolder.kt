package com.famous.paperplane.zhihu.daily

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.famous.paperplane.business_base.ContentType
import com.famous.paperplane.business_base.DetailActivityParam
import com.famous.paperplane.business_base.app.appModule
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
        val context = itemView.context ?: return
        val intent = Intent(context, appModule.detailActivityClass()).apply {
            putExtra(DetailActivityParam.KEY_ARTICLE_ID, item.id)
            putExtra(DetailActivityParam.KEY_ARTICLE_TYPE, ContentType.TYPE_ZHIHU_DAILY)
            putExtra(DetailActivityParam.KEY_ARTICLE_TITLE, item.title)
            putExtra(DetailActivityParam.KEY_ARTICLE_IS_FAVORITE, item.isFavorite)
        }
        context.startActivity(intent)
    }
}