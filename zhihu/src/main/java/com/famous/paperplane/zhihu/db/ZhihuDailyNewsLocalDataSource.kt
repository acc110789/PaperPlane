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

package com.famous.paperplane.zhihu.db

import com.famous.paperplane.business_base.LocalDataNotFoundException
import com.famous.paperplane.business_base.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.getKoin

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Concrete implementation of a [ZhihuDailyNewsQuestion] data source as database.
 */

object ZhihuDailyNewsLocalDataSource {

    private val mZhihuDailyNewsDao: ZhihuDailyNewsDao by getKoin().inject()

    suspend fun getZhihuDailyNews(date: Long): Result<List<ZhihuDailyNewsQuestion>> = withContext(IO) {
        val news = mZhihuDailyNewsDao.queryAllByDate(date)
        if (news.isNotEmpty()) Result.Success(news) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun getFavorites(): Result<List<ZhihuDailyNewsQuestion>> = withContext(IO) {
        val favorites = mZhihuDailyNewsDao.queryAllFavorites()
        if (favorites.isNotEmpty()) Result.Success(favorites) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun getItem(itemId: Int): Result<ZhihuDailyNewsQuestion> = withContext(IO) {
        val item = mZhihuDailyNewsDao.queryItemById(itemId)
        if (item != null) Result.Success(item) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun favoriteItem(itemId: Int, favorite: Boolean) = withContext(IO) {
        val item = mZhihuDailyNewsDao.queryItemById(itemId) ?: return@withContext
        item.isFavorite = favorite
        mZhihuDailyNewsDao.update(item)
    }

    suspend fun saveAll(list: List<ZhihuDailyNewsQuestion>) = withContext(IO) {
        mZhihuDailyNewsDao.insertAll(list)
    }
}
