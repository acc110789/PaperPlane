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

import com.famous.paperplane.business_base.Result
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsLocalDataSource
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import com.famous.paperplane.zhihu.net.ZhihuDailyNewsRemoteDataSource

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Concrete implementation to load [ZhihuDailyNewsQuestion]s from the data sources into a cache.
 *
 * Use the remote data source firstly, which is obtained from the server.
 * If the remote data was not available, then use the local data source,
 * which was from the locally persisted in database.
 */

class ZhihuDailyNewsRepository(
    private val mLocalDataSource: ZhihuDailyNewsLocalDataSource,
    private val mRemoteDataSource: ZhihuDailyNewsRemoteDataSource
) {

    private var mCachedItems = mutableMapOf<Int, ZhihuDailyNewsQuestion>()

    suspend fun getZhihuDailyNewsFromCache(): Result<List<ZhihuDailyNewsQuestion>> {
        return Result.Success(mCachedItems.values.toList())
    }

    suspend fun getZhihuDailyNews(date: Long): Result<List<ZhihuDailyNewsQuestion>> {
        return mRemoteDataSource.getZhihuDailyNews(date)
    }

    suspend fun getFavorites(): Result<List<ZhihuDailyNewsQuestion>> =
        mLocalDataSource.getFavorites()

    suspend fun getItem(itemId: Int): Result<ZhihuDailyNewsQuestion> {
        val cachedItem = getItemWithId(itemId)

        if (cachedItem != null) {
            return Result.Success(cachedItem)
        }

        return mLocalDataSource.getItem(itemId).also {
            if (it is Result.Success) {
                mCachedItems[it.data.id] = it.data
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

    suspend fun saveAll(list: List<ZhihuDailyNewsQuestion>) {
        mLocalDataSource.saveAll(list)

        for (item in list) {
            mCachedItems[item.id] = item
        }
    }

    private fun refreshCache(clearCache: Boolean, list: List<ZhihuDailyNewsQuestion>) {

        if (clearCache) {
            mCachedItems.clear()
        }
        for (item in list) {
            mCachedItems[item.id] = item
        }
    }

    private fun getItemWithId(id: Int): ZhihuDailyNewsQuestion? =
        if (mCachedItems.isEmpty()) null else mCachedItems[id]

}
