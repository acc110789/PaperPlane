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

package com.marktony.zhihudaily.database.dao

import androidx.room.*
import com.marktony.zhihudaily.data.GuokrHandpickNewsResult

/**
 * Created by lizhaotailang on 2017/6/15.
 *
 * Interface for database access on [GuokrHandpickNewsResult] related operations.
 */

@Dao
interface GuokrHandpickNewsDao {

    @Query("SELECT * FROM guokr_handpick_news LIMIT :limit OFFSET :offset")
    fun queryAllByOffsetAndLimit(offset: Int, limit: Int): List<GuokrHandpickNewsResult>

    @Query("SELECT * FROM guokr_handpick_news WHERE id = :id")
    fun queryItemById(id: Int): GuokrHandpickNewsResult?

    @Query("SELECT * FROM guokr_handpick_news WHERE favorite = 1")
    fun queryAllFavorites(): List<GuokrHandpickNewsResult>

    @Query("SELECT * FROM guokr_handpick_news WHERE (timestamp < :timestamp) AND (favorite = 0)")
    fun queryAllTimeoutItems(timestamp: Long): List<GuokrHandpickNewsResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(items: List<GuokrHandpickNewsResult>)

    @Update
    fun update(item: GuokrHandpickNewsResult)

    @Delete
    fun delete(item: GuokrHandpickNewsResult)

}
