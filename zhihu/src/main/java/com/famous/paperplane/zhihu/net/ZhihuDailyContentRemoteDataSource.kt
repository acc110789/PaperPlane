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

import com.famous.paperplane.zhihu.db.ZhihuDailyContent
import com.famous.paperplane.business_base.RemoteDataNotFoundException
import com.famous.paperplane.business_base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by lizhaotailang on 2017/5/26.
 *
 * Implementation of the [ZhihuDailyContent] data source that accesses network.
 */

class ZhihuDailyContentRemoteDataSource internal constructor(
    private val mZhihuDailyService: ZhihuDailyService
) {

    suspend fun getZhihuDailyContent(id: Int): Result<ZhihuDailyContent> = withContext(Dispatchers.IO) {
        try {
            val response = mZhihuDailyService.getZhihuContent(id).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
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
}
