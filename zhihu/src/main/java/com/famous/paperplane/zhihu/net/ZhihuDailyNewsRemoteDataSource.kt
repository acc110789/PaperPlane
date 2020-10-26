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

import com.famous.paperplane.business_base.RemoteDataNotFoundException
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import com.famous.paperplane.zhihu.utils.formatZhihuDailyDateToTodayToString
import com.famous.paperplane.zhihu.utils.formatZhihuDailyDateToTomorrowToString
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.getKoin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Implementation of the [ZhihuDailyNews] data source that accesses network.
 */

class ZhihuDailyNewsRemoteDataSource(private val mZhihuDailyService: ZhihuDailyService) {

    internal suspend fun getZhihuDailyNews(date: Long): Result<List<ZhihuDailyNewsQuestion>> {
        val firstDayResult = getZhihuDailyNewsInner(formatZhihuDailyDateToTomorrowToString(date))
        if (!firstDayResult.isSuccess) return firstDayResult

        val secondDayResult = getZhihuDailyNewsInner(formatZhihuDailyDateToTodayToString(date))
        if (!secondDayResult.isSuccess) return firstDayResult

        val finalItemList = mutableListOf<ZhihuDailyNewsQuestion>()
        val finalItemIdSet = mutableSetOf<Int>()
        for (item in firstDayResult.successData + secondDayResult.successData) {
            if (finalItemIdSet.contains(item.id)) continue
            finalItemIdSet.add(item.id)

            finalItemList.add(item)
        }
        return Result.Success(finalItemList)
    }

    private suspend fun getZhihuDailyNewsInner(date: String): Result<List<ZhihuDailyNewsQuestion>> = suspendCancellableCoroutine { continuation ->
        val callback = object: Callback<ZhihuDailyNews> {
            override fun onResponse(
                call: Call<ZhihuDailyNews>,
                response: Response<ZhihuDailyNews>
            ) {
                if (!response.isSuccessful) {
                    continuation.resume(Result.Error(RemoteDataNotFoundException()))
                    return
                }

                val body = response.body()
                if (body == null) {
                    continuation.resume(Result.Error(RemoteDataNotFoundException()))
                    return
                }

                val stories = body.stories
                if (stories.isEmpty()) {
                    continuation.resume(Result.Error(RemoteDataNotFoundException()))
                    return
                }
                continuation.resume(Result.Success(stories))
            }

            override fun onFailure(call: Call<ZhihuDailyNews>, t: Throwable) {
                continuation.resume(Result.Error(t))
            }

        }
        mZhihuDailyService.getZhihuList(date).enqueue(callback)
    }
}