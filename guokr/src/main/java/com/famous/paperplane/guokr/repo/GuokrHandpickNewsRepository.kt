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

package com.famous.paperplane.guokr.repo

import com.famous.paperplane.business_base.Result
import com.famous.paperplane.guokr.db.GuokrHandpickNewsLocalDataSource
import com.famous.paperplane.guokr.entity.GuokrHandpickNewsResult
import com.famous.paperplane.guokr.net.GuokrHandpickNewsRemoteDataSource
import com.famous.paperplane.guokr.utils.formatGuokrHandpickTimeStringToLong
import java.util.*

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Concrete implementation to load [GuokrHandpickNewsResult] from the data sources into a cache.
 *
 * Use the remote data source firstly, which is obtained from the server.
 * If the remote data was not available, then use the local data source,
 * which was from the locally persisted in database.
 */

class GuokrHandpickNewsRepository internal constructor(
    private val mRemoteDataSource: GuokrHandpickNewsRemoteDataSource,
    private val mLocalDataSource: GuokrHandpickNewsLocalDataSource
)  {

    private var mCachedItems: MutableMap<Int, GuokrHandpickNewsResult> = LinkedHashMap()

    suspend fun getGuokrHandpickNews(forceUpdate: Boolean, clearCache: Boolean, offset: Int, limit: Int): Result<List<GuokrHandpickNewsResult>> {
        if (!forceUpdate) {
            return Result.Success(mCachedItems.values.toList())
        }

        val remoteResult = mRemoteDataSource.getGuokrHandpickNews(false, clearCache, offset, limit)
        return if (remoteResult is Result.Success) {
            refreshCache(clearCache, remoteResult.data)
            saveAll(remoteResult.data)

            remoteResult
        } else {
            mLocalDataSource.getGuokrHandpickNews(false, clearCache, offset, limit).also {
                if (it is Result.Success) {
                    refreshCache(clearCache, it.data)
                }
            }
        }
    }

    suspend fun getFavorites(): Result<List<GuokrHandpickNewsResult>> = mLocalDataSource.getFavorites()

     suspend fun getItem(itemId: Int): Result<GuokrHandpickNewsResult> {
        val item = getItemWithId(itemId)

        if (item != null) {
            return Result.Success(item)
        }

        return mLocalDataSource.getItem(itemId).apply {
            if (this is Result.Success) {
                mCachedItems[this.data.id] = this.data
            }
        }
    }

    suspend fun favoriteItem(itemId: Int, favorite: Boolean) {
        mLocalDataSource.favoriteItem(itemId, favorite)

        val cachedItem = getItemWithId(itemId)
        if (cachedItem != null) {
            cachedItem.isFavorite = favorite
        }
    }

    suspend fun saveAll(list: List<GuokrHandpickNewsResult>) {
        for (item in list) {
            item.timestamp = formatGuokrHandpickTimeStringToLong(item.datePublished)
            mCachedItems[item.id] = item
        }

        mLocalDataSource.saveAll(list)
    }

    private fun refreshCache(clearCache: Boolean, list: List<GuokrHandpickNewsResult>) {
        if (clearCache) {
            mCachedItems.clear()
        }
        for (item in list) {
            mCachedItems[item.id] = item
        }
    }

    private fun getItemWithId(itemId: Int): GuokrHandpickNewsResult? = if (mCachedItems.isEmpty()) null else mCachedItems[itemId]

}
