/*
 * Copyright 2016 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.famous.paperplane.zhihu.net

import androidx.annotation.VisibleForTesting
import com.famous.paperplane.business_base.RemoteDataNotFoundException
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.BuildConfig
import com.famous.paperplane.zhihu.base.ZhihuDailyNewsDataSource
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import com.famous.paperplane.zhihu.utils.formatZhihuDailyDateLongToString
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Implementation of the [ZhihuDailyNews] data source that accesses network.
 */

class ZhihuDailyNewsRemoteDataSource private constructor() : ZhihuDailyNewsDataSource {

    private val mZhihuDailyService: ZhihuDailyService by lazy {
        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        httpClientBuilder.retryOnConnectionFailure(true)

        val retrofit = Retrofit.Builder()
                .baseUrl(ZHIHU_DAILY_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

        retrofit.create(ZhihuDailyService::class.java)
    }

    companion object {

        private var INSTANCE: ZhihuDailyNewsRemoteDataSource? = null

        @JvmStatic
        fun getInstance(): ZhihuDailyNewsRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(ZhihuDailyNewsRemoteDataSource::javaClass) {
                    INSTANCE = ZhihuDailyNewsRemoteDataSource()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }

    }

    override suspend fun getZhihuDailyNews(forceUpdate: Boolean, clearCache: Boolean, date: Long): Result<List<ZhihuDailyNewsQuestion>> = withContext(IO) {
        try {
            val response = mZhihuDailyService.getZhihuList(formatZhihuDailyDateLongToString(date)).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.stories.isNotEmpty()) {
                        Result.Success(it.stories)
                    } else {
                        Result.Error(RemoteDataNotFoundException())
                    }
                } ?: run {
                    Result.Error(RemoteDataNotFoundException())
                }
            } else {
                Result.Error(RemoteDataNotFoundException())
            }
        } catch (e: Exception) {
            Result.Error(RemoteDataNotFoundException())
        }

    }

    // Not required because the [com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository] handles the logic of refreshing the
    // news from all the available data sources.
    override suspend fun getFavorites(): Result<List<ZhihuDailyNewsQuestion>> = Result.Error(RemoteDataNotFoundException())

    // Not required because the [com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository] handles the logic of refreshing the
    // news from all the available data sources.
    override suspend fun getItem(itemId: Int): Result<ZhihuDailyNewsQuestion> = Result.Error(RemoteDataNotFoundException())

    override suspend fun favoriteItem(itemId: Int, favorite: Boolean) {
        // Not required because the [com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository] handles the logic of refreshing the
        // news from all the available data sources.
    }

    override suspend fun saveAll(list: List<ZhihuDailyNewsQuestion>) {
        // Not required because the [com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository] handles the logic of refreshing the
        // news from all the available data sources.
    }

}