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

package com.famous.paperplane.douban.repo

import com.famous.paperplane.business_base.Result
import com.famous.paperplane.douban.db.DoubanMomentContentLocalDataSource
import com.famous.paperplane.douban.entity.DoubanMomentContent
import com.famous.paperplane.douban.net.DoubanMomentContentRemoteDataSource

/**
 * Created by lizhaotailang on 2017/5/25.
 *
 * Concrete implementation to load [DoubanMomentContent] from the data sources into a cache.
 *
 * Use the remote data source firstly, which is obtained from the server.
 * If the remote data was not available, then use the local data source,
 * which was from the locally persisted in database.
 *
 */

class DoubanMomentContentRepository internal constructor(
    private val mRemoteDataSource: DoubanMomentContentRemoteDataSource,
    private val mLocalDataSource: DoubanMomentContentLocalDataSource
) {

    private var mContent: DoubanMomentContent? = null

    suspend fun getDoubanMomentContent(id: Int): Result<DoubanMomentContent> {
        if (mContent != null && mContent?.id == id) {
            return Result.Success(mContent!!)
        }

        val remoteResult = mRemoteDataSource.getDoubanMomentContent(id)
        return if (remoteResult is Result.Success) {
            mContent = remoteResult.data
            saveContent(remoteResult.data)

            remoteResult
        } else {
            mLocalDataSource.getDoubanMomentContent(id).also {
                if (it is Result.Success) {
                    mContent = it.data
                }
            }
        }
    }

    suspend fun saveContent(content: DoubanMomentContent) {
        mLocalDataSource.saveContent(content)
    }

}
