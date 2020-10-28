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

package com.famous.paperplane.douban.net

import com.famous.paperplane.business_base.RemoteDataNotFoundException
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.douban.entity.DoubanMomentContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by lizhaotailang on 2017/5/25.
 *
 * Implementation of the [DoubanMomentContent] data source that accesses network.
 */

class DoubanMomentContentRemoteDataSource internal constructor(private val mDoubanMomentService: DoubanMomentService) {

    suspend fun getDoubanMomentContent(id: Int): Result<DoubanMomentContent> =
        withContext(Dispatchers.IO) {
            try {
                val response = mDoubanMomentService.getDoubanContent(id).execute()
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