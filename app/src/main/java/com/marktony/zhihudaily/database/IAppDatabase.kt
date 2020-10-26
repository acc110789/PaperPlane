package com.marktony.zhihudaily.database

import com.famous.paperplane.zhihu.db.ZhihuDailyContentDao
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsDao
import com.marktony.zhihudaily.database.dao.DoubanMomentContentDao
import com.marktony.zhihudaily.database.dao.DoubanMomentNewsDao
import com.marktony.zhihudaily.database.dao.GuokrHandpickContentDao
import com.marktony.zhihudaily.database.dao.GuokrHandpickNewsDao

interface IAppDatabase {
    fun zhihuDailyNewsDao(): ZhihuDailyNewsDao

    fun zhihuDailyContentDao(): ZhihuDailyContentDao

    fun doubanMomentNewsDao(): DoubanMomentNewsDao

    fun doubanMomentContentDao(): DoubanMomentContentDao

    fun guokrHandpickNewsDao(): GuokrHandpickNewsDao

    fun guokrHandpickContentDao(): GuokrHandpickContentDao
}