package com.famous.paperplane.douban

import com.famous.paperplane.douban.db.DoubanMomentContentLocalDataSource
import com.famous.paperplane.douban.db.DoubanMomentNewsLocalDataSource
import com.famous.paperplane.douban.net.DOUBAN_MOMENT_BASE
import com.famous.paperplane.douban.net.DoubanMomentContentRemoteDataSource
import com.famous.paperplane.douban.net.DoubanMomentNewsRemoteDataSource
import com.famous.paperplane.douban.net.DoubanMomentService
import com.famous.paperplane.douban.repo.DoubanMomentContentRepository
import com.famous.paperplane.douban.repo.DoubanMomentNewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val doubanKoinModule = module {

    //DoubanMomentService
    single {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(24, TimeUnit.SECONDS)
            .readTimeout(24, TimeUnit.SECONDS)
            .writeTimeout(24, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        httpClientBuilder.retryOnConnectionFailure(true)

        val retrofit = Retrofit.Builder()
            .baseUrl(DOUBAN_MOMENT_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()

        retrofit.create(DoubanMomentService::class.java)
    }

    //DoubanMomentNewsRepository
    single { DoubanMomentNewsRepository(get(), get()) }

    //DoubanMomentNewsLocalDataSource
    single { DoubanMomentNewsLocalDataSource(get()) }

    //DoubanMomentNewsRemoteDataSource
    single { DoubanMomentNewsRemoteDataSource(get()) }

    //DoubanMomentContentRepository
    single { DoubanMomentContentRepository(get(), get()) }

    //DoubanMomentContentLocalDataSource
    single { DoubanMomentContentLocalDataSource(get()) }

    //DoubanMomentContentRemoteDataSource
    single { DoubanMomentContentRemoteDataSource(get()) }
}