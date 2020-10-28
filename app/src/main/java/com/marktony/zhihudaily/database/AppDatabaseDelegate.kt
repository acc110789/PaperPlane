package com.marktony.zhihudaily.database

import com.famous.paperplane.zhihu.db.ZhihuDailyContentDao
import com.famous.paperplane.zhihu.db.ZhihuDailyNewsDao
import com.famous.paperplane.douban.db.DoubanMomentContentDao
import com.famous.paperplane.douban.db.DoubanMomentNewsDao
import com.famous.paperplane.guokr.db.GuokrHandpickContentDao
import com.famous.paperplane.guokr.db.GuokrHandpickNewsDao
import org.koin.java.KoinJavaComponent.getKoin

object AppDatabaseDelegate: IAppDatabase {

    private val appDatabase by getKoin().inject<AppDatabase>()

    override fun zhihuDailyNewsDao(): ZhihuDailyNewsDao = appDatabase.zhihuDailyNewsDao()

    override fun doubanMomentNewsDao(): DoubanMomentNewsDao = appDatabase.doubanMomentNewsDao()

    override fun guokrHandpickNewsDao(): GuokrHandpickNewsDao = appDatabase.guokrHandpickNewsDao()

    override fun zhihuDailyContentDao(): ZhihuDailyContentDao = appDatabase.zhihuDailyContentDao()

    override fun doubanMomentContentDao(): DoubanMomentContentDao = appDatabase.doubanMomentContentDao()

    override fun guokrHandpickContentDao(): GuokrHandpickContentDao = appDatabase.guokrHandpickContentDao()

}