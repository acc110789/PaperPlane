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

package com.marktony.zhihudaily.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.famous.paperplane.zhihu.db.ZhihuDailyContent
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsQuestion
import com.marktony.zhihudaily.data.*
import org.koin.dsl.module

/**
 * Created by lizhaotailang on 2017/6/15.
 *
 * Main database description.
 */

private const val DATABASE_NAME = "paper-plane-db"

val appDataBaseKoinModule = module {
    //AppDatabase
    single {
        val application = get<Application>()
        val builder = Room.databaseBuilder(
            application,
            AppDatabase::class.java, DATABASE_NAME
        )
        builder.build()
    }

    //ZhihuDailyNewsDao
    single { get<AppDatabase>().zhihuDailyNewsDao() }

    //ZhihuDailyContentDao
    single { get<AppDatabase>().zhihuDailyContentDao() }

    //DoubanMomentNewsDao
    single { get<AppDatabase>().doubanMomentNewsDao() }

    //doubanMomentContentDao
    single { get<AppDatabase>().doubanMomentContentDao() }

    //GuokrHandpickNewsDao
    single { get<AppDatabase>().guokrHandpickNewsDao() }

    //GuokrHandpickContentDao
    single { get<AppDatabase>().guokrHandpickContentDao() }
}

@Database(entities = [
    ZhihuDailyNewsQuestion::class,
    DoubanMomentNewsPosts::class,
    GuokrHandpickNewsResult::class,
    ZhihuDailyContent::class,
    DoubanMomentContent::class,
    GuokrHandpickContentResult::class
], version = 1)
abstract class AppDatabase : IAppDatabase, RoomDatabase()
