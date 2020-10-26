package com.famous.paperplane.zhihu

import android.view.ViewGroup
import com.famous.paperplane.business_base.layoutInflater
import com.famous.paperplane.zhihu.base.ZhihuDailyNewsRepository
import com.famous.paperplane.zhihu.daily.*
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsLocalDataSource
import com.famous.paperplane.zhihu.net.ZHIHU_DAILY_BASE
import com.famous.paperplane.zhihu.net.ZhihuDailyNewsRemoteDataSource
import com.famous.paperplane.zhihu.net.ZhihuDailyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val zhihuDailyScopeViewModel = named("zhihuDailyViewModel")

val zhihuKoinModule = module {

    //ZhihuDailyNewsLocalDataSource
    single { ZhihuDailyNewsLocalDataSource(get()) }

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

    //ZhihuDailyNewsRemoteDataSource
    single { ZhihuDailyNewsRemoteDataSource(get()) }

    //ZhihuDailyNewsRepository
    single { ZhihuDailyNewsRepository(get(), get()) }

    scope<ZhihuDailyFragment> {

        viewModel { ZhihuDailyViewModel(get()) }

        //ZhihuDailyNewsItemViewHolder
        factory { param ->
            val parent: ViewGroup = param.get()
            val itemView = parent.layoutInflater().inflate(R.layout.item_universal_layout, parent, false)
            ZhihuDailyNewsItemViewHolder(itemView, get(zhihuDailyScopeViewModel))
        }

        factory {
            val viewHolderFactory: ZhihuDailyNewsItemViewHolderFactory = { get { parametersOf(it) } }
            ZhihuDailyNewsAdapter(get(zhihuDailyScopeViewModel), viewHolderFactory)
        }
    }
}