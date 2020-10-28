package com.marktony.zhihudaily.injection

import com.famous.paperplane.douban.repo.DoubanMomentContentRepository
import com.famous.paperplane.douban.repo.DoubanMomentNewsRepository
import com.famous.paperplane.guokr.repo.GuokrHandpickContentRepository
import com.famous.paperplane.guokr.repo.GuokrHandpickNewsRepository
import com.famous.paperplane.zhihu.repo.ZhihuDailyContentRepository
import com.famous.paperplane.zhihu.repo.ZhihuDailyNewsRepository
import com.marktony.zhihudaily.util.AppExecutors
import org.koin.java.KoinJavaComponent.getKoin

object Injection {

    fun provideZhihuDailyNewsRepository(): ZhihuDailyNewsRepository = getKoin().get()

    fun provideZhihuDailyContentRepository(): ZhihuDailyContentRepository = getKoin().get()

    fun provideGuokrHandpickNewsRepository(): GuokrHandpickNewsRepository = getKoin().get()

    fun provideGuokrHandpickContentRepository(): GuokrHandpickContentRepository = getKoin().get()

    fun provideDoubanMomentNewsRepository(): DoubanMomentNewsRepository = getKoin().get()

    fun provideDoubanMomentContentRepository(): DoubanMomentContentRepository = getKoin().get()

}