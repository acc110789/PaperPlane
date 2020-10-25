package com.famous.paperplane.zhihu

import android.view.ViewGroup
import com.famous.paperplane.business_base.layoutInflater
import com.famous.paperplane.zhihu.daily.ZhihuDailyNewsAdapter
import com.famous.paperplane.zhihu.daily.ZhihuDailyNewsItemContext
import com.famous.paperplane.zhihu.daily.ZhihuDailyNewsItemViewHolder
import com.famous.paperplane.zhihu.daily.ZhihuDailyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val zhihuModule = module {

    viewModel { ZhihuDailyViewModel() }

    factory { param ->
        val itemContext = param.component1<ZhihuDailyNewsItemContext>()
        ZhihuDailyNewsAdapter(itemContext)
    }

    factory {param ->
        val parent: ViewGroup = param.component1()
        val context: ZhihuDailyNewsItemContext = param.component2()
        val itemView = parent.layoutInflater().inflate(R.layout.item_universal_layout, parent, false)
        ZhihuDailyNewsItemViewHolder(
            itemView,
            context
        )
    }
}