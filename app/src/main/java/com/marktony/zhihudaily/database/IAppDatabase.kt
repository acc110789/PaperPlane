package com.marktony.zhihudaily.database

import com.famous.paperplane.zhihu.db.ZhihuDailyContentDao
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsDao
import com.famous.paperplane.douban.db.DoubanMomentContentDao
import com.famous.paperplane.douban.db.DoubanMomentNewsDao
import com.famous.paperplane.guokr.db.GuokrHandpickContentDao
import com.famous.paperplane.guokr.db.GuokrHandpickNewsDao

interface IAppDatabase {
    fun zhihuDailyNewsDao(): ZhihuDailyNewsDao

    fun zhihuDailyContentDao(): ZhihuDailyContentDao

    fun doubanMomentNewsDao(): DoubanMomentNewsDao

    fun doubanMomentContentDao(): DoubanMomentContentDao

    fun guokrHandpickNewsDao(): GuokrHandpickNewsDao

    fun guokrHandpickContentDao(): GuokrHandpickContentDao
}