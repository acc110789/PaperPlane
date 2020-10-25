package com.famous.paperplane.zhihu

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val zhihuModule = module {

    viewModel { ZhihuDailyViewModel() }

    factory { param ->
        val itemContext = param.component1<ZhihuDailyNewsItemContext>()
        ZhihuDailyNewsAdapter(itemContext)
    }
}