package com.famous.paperplane.guokr

import com.famous.paperplane.guokr.db.GuokrHandpickContentLocalDataSource
import com.famous.paperplane.guokr.db.GuokrHandpickNewsLocalDataSource
import com.famous.paperplane.guokr.net.GUOKR_HANDPICK_BASE
import com.famous.paperplane.guokr.net.GuokrHandpickContentRemoteDataSource
import com.famous.paperplane.guokr.net.GuokrHandpickNewsRemoteDataSource
import com.famous.paperplane.guokr.net.GuokrHandpickService
import com.famous.paperplane.guokr.repo.GuokrHandpickContentRepository
import com.famous.paperplane.guokr.repo.GuokrHandpickNewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val guokrKoinModule = module {

    //GuokrHandpickService
    single {
        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        httpClientBuilder.retryOnConnectionFailure(true)

        val retrofit = Retrofit.Builder()
            .baseUrl(GUOKR_HANDPICK_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()

        retrofit.create(GuokrHandpickService::class.java)
    }

    //GuokrHandpickNewsRemoteDataSource
    single {
        GuokrHandpickNewsRemoteDataSource(get())
    }

    //GuokrHandpickContentRemoteDataSource
    single { GuokrHandpickContentRemoteDataSource(get()) }

    //GuokrHandpickContentLocalDataSource
    single { GuokrHandpickContentLocalDataSource(get()) }

    //GuokrHandpickNewsLocalDataSource
    single { GuokrHandpickNewsLocalDataSource(get()) }

    //GuokrHandpickContentRepository
    single { GuokrHandpickContentRepository(get(), get()) }

    //GuokrHandpickNewsRepository
    single { GuokrHandpickNewsRepository(get(), get()) }
}