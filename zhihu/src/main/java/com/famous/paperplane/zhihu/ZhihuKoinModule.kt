package com.famous.paperplane.zhihu

import android.view.ViewGroup
import com.famous.paperplane.business_base.layoutInflater
import com.famous.paperplane.zhihu.daily.ZhihuDailyNewsAdapter
import com.famous.paperplane.zhihu.daily.ZhihuDailyNewsItemContext
import com.famous.paperplane.zhihu.daily.ZhihuDailyNewsItemViewHolder
import com.famous.paperplane.zhihu.daily.ZhihuDailyViewModel
import com.famous.paperplane.zhihu.net.ZHIHU_DAILY_BASE
import com.famous.paperplane.zhihu.net.ZhihuDailyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val zhihuKoinModule = module {

    viewModel { ZhihuDailyViewModel() }

    factory { param ->
        val itemContext = param.component1<ZhihuDailyNewsItemContext>()
        ZhihuDailyNewsAdapter(itemContext)
    }

    factory {param ->
        val parent: ViewGroup = param.component1()
        val context: ZhihuDailyNewsItemContext = param.component2()
        val itemView = parent.layoutInflater().inflate(R.layout.item_universal_layout, parent, false)
        ZhihuDailyNewsItemViewHolder(itemView, context)
    }

    single<ZhihuDailyService> {
        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.retryOnConnectionFailure(true)

        val retrofit = Retrofit.Builder()
            .baseUrl(ZHIHU_DAILY_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()

        retrofit.create(ZhihuDailyService::class.java)
    }
}