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

package com.famous.paperplane.douban.db

import com.famous.paperplane.business_base.LocalDataNotFoundException
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.douban.entity.DoubanMomentNewsPosts
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * * Concrete implementation of a [DoubanMomentNewsPosts] data source as database .
 */

class DoubanMomentNewsLocalDataSource internal constructor(
    private val mDoubanMomentNewsDao: DoubanMomentNewsDao
) {

    suspend fun getDoubanMomentNews(
        forceUpdate: Boolean,
        clearCache: Boolean,
        date: Long
    ): Result<List<DoubanMomentNewsPosts>> = withContext(IO) {
        val news = mDoubanMomentNewsDao.queryAllTimeoutItems(date)
        if (news.isNotEmpty()) Result.Success(news) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun getFavorites(): Result<List<DoubanMomentNewsPosts>> = withContext(IO) {
        val favorites = mDoubanMomentNewsDao.queryAllFavorites()
        if (favorites.isNotEmpty()) Result.Success(favorites) else Result.Error(
            LocalDataNotFoundException()
        )
    }

    suspend fun getItem(id: Int): Result<DoubanMomentNewsPosts> = withContext(IO) {
        val item = mDoubanMomentNewsDao.queryItemById(id)
        if (item != null) Result.Success(item) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun favoriteItem(itemId: Int, favorite: Boolean) {
        withContext(IO) {
            mDoubanMomentNewsDao.queryItemById(itemId)?.let {
                it.isFavorite = favorite
                mDoubanMomentNewsDao.update(it)
            }
        }
    }

    suspend fun saveAll(list: List<DoubanMomentNewsPosts>) {
        withContext(IO) {
            mDoubanMomentNewsDao.insertAll(list)
        }
    }

}
