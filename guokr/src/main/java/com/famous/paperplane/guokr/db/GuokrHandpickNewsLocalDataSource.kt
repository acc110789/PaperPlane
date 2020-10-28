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

package com.famous.paperplane.guokr.db

import com.famous.paperplane.business_base.LocalDataNotFoundException
import com.famous.paperplane.business_base.Result
import com.famous.paperplane.guokr.entity.GuokrHandpickNewsResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Concrete implementation of a [GuokrHandpickNewsResult] data source as database.
 */

class GuokrHandpickNewsLocalDataSource internal constructor(
        private val mGuokrHandpickNewsDao: GuokrHandpickNewsDao
) {

    suspend fun getGuokrHandpickNews(forceUpdate: Boolean, clearCache: Boolean, offset: Int, limit: Int): Result<List<GuokrHandpickNewsResult>> = withContext(IO) {
        val list = mGuokrHandpickNewsDao.queryAllByOffsetAndLimit(offset, limit)
        if (list.isNotEmpty()) Result.Success(list) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun getFavorites(): Result<List<GuokrHandpickNewsResult>> = withContext(IO) {
        val favorites = mGuokrHandpickNewsDao.queryAllFavorites()
        if (favorites.isNotEmpty()) Result.Success(favorites) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun getItem(itemId: Int): Result<GuokrHandpickNewsResult> = withContext(IO) {
        val item = mGuokrHandpickNewsDao.queryItemById(itemId)
        if (item != null) Result.Success(item) else Result.Error(LocalDataNotFoundException())
    }

    suspend fun favoriteItem(itemId: Int, favorite: Boolean) {
        withContext(IO) {
            mGuokrHandpickNewsDao.queryItemById(itemId)?.let {
                it.isFavorite = favorite
                mGuokrHandpickNewsDao.update(it)
            }
        }
    }

    suspend fun saveAll(list: List<GuokrHandpickNewsResult>) {
        withContext(IO) {
            mGuokrHandpickNewsDao.insertAll(list)
        }
    }

}
