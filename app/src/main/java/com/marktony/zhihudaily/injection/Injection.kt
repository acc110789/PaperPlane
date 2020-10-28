package com.marktony.zhihudaily.injection

import android.content.Context
import com.famous.paperplane.douban.repo.DoubanMomentContentRepository
import com.famous.paperplane.douban.repo.DoubanMomentNewsRepository
import com.famous.paperplane.guokr.db.GuokrHandpickContentLocalDataSource
import com.famous.paperplane.guokr.db.GuokrHandpickNewsLocalDataSource
import com.famous.paperplane.guokr.net.GuokrHandpickContentRemoteDataSource
import com.famous.paperplane.guokr.net.GuokrHandpickNewsRemoteDataSource
import com.famous.paperplane.guokr.repo.GuokrHandpickContentRepository
import com.famous.paperplane.guokr.repo.GuokrHandpickNewsRepository
import com.famous.paperplane.zhihu.repo.ZhihuDailyContentRepository
import com.famous.paperplane.zhihu.repo.ZhihuDailyNewsRepository
import com.marktony.zhihudaily.database.AppDatabaseDelegate
import com.marktony.zhihudaily.util.AppExecutors
import org.koin.java.KoinJavaComponent.getKoin

object Injection {

    private val appExecutors: AppExecutors = AppExecutors()

    fun provideZhihuDailyNewsRepository(): ZhihuDailyNewsRepository = getKoin().get()

    fun provideZhihuDailyContentRepository(): ZhihuDailyContentRepository = getKoin().get()

    fun provideGuokrHandpickNewsRepository(context: Context): GuokrHandpickNewsRepository = GuokrHandpickNewsRepository.getInstance(
        GuokrHandpickNewsRemoteDataSource.getInstance(appExecutors), GuokrHandpickNewsLocalDataSource.getInstance(appExecutors, AppDatabaseDelegate.guokrHandpickNewsDao()))

    fun provideGuokrHandpickContentRepository(context: Context): GuokrHandpickContentRepository = GuokrHandpickContentRepository.getInstance(
        GuokrHandpickContentRemoteDataSource.getInstance(appExecutors), GuokrHandpickContentLocalDataSource.getInstance(appExecutors, AppDatabaseDelegate.guokrHandpickContentDao()))

    fun provideDoubanMomentNewsRepository(): DoubanMomentNewsRepository = getKoin().get()

    fun provideDoubanMomentContentRepository(): DoubanMomentContentRepository = getKoin().get()

}