package com.famous.paperplane.douban.net

import com.famous.paperplane.douban.entity.DoubanMomentContent
import com.famous.paperplane.douban.entity.DoubanMomentNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val DOUBAN_MOMENT_BASE = "https://moment.douban.com/api/"

interface DoubanMomentService {

    @GET("stream/date/{date}")
    fun getDoubanList(@Path("date") date: String): Call<DoubanMomentNews>

    @GET("post/{id}")
    fun getDoubanContent(@Path("id") id: Int): Call<DoubanMomentContent>

}