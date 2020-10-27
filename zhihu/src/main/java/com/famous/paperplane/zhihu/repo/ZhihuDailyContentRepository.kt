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

package com.famous.paperplane.zhihu.repo

import com.famous.paperplane.zhihu.db.ZhihuDailyContent
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.db.ZhihuDailyContentLocalDataSource
import com.famous.paperplane.zhihu.net.ZhihuDailyContentRemoteDataSource

/**
 * Created by lizhaotailang on 2017/5/26.
 *
 * Concrete implementation to load [ZhihuDailyContent] from the data sources into a cache.
 *
 * Use the remote data source firstly, which is obtained from the server.
 * If the remote data was not available, then use the local data source,
 * which was from the locally persisted in database.
 */

class ZhihuDailyContentRepository internal constructor(
    private val mRemoteDataSource: ZhihuDailyContentRemoteDataSource,
    private val mLocalDataSource: ZhihuDailyContentLocalDataSource
) {

    private var mContent: ZhihuDailyContent? = null

    suspend fun getZhihuDailyContent(id: Int): Result<ZhihuDailyContent> {
        if (mContent != null && mContent?.id == id) {
            return Result.Success(mContent!!)
        }

        val remoteResult = mRemoteDataSource.getZhihuDailyContent(id)
        return if (remoteResult is Result.Success) {
            mContent = remoteResult.data
            saveContent(remoteResult.data)

            remoteResult
        } else {
            mLocalDataSource.getZhihuDailyContent(id).also {
                if (it is Result.Success) {
                    mContent = it.data
                }
            }
        }
    }

    suspend fun saveContent(content: ZhihuDailyContent) {
        mLocalDataSource.saveContent(content)
    }

}
