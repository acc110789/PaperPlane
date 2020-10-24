package com.famous.paperplane.zhihu.net

import com.famous.paperplane.zhihu.db.ZhihuDailyContent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val ZHIHU_DAILY_BASE = "https://news-at.zhihu.com/api/4/news/"

interface ZhihuDailyService {

    @GET("before/{date}")
    fun getZhihuList(@Path("date") date: String): Call<ZhihuDailyNews>

    @GET("{id}")
    fun getZhihuContent(@Path("id") id: Int): Call<ZhihuDailyContent>

}