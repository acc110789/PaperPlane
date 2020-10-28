package com.famous.paperplane.guokr.net

import com.famous.paperplane.guokr.entity.GuokrHandpickContent
import com.famous.paperplane.guokr.entity.GuokrHandpickNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal const val GUOKR_HANDPICK_BASE = "http://apis.guokr.com/minisite/"

interface GuokrHandpickService {

    @GET("article.json?retrieve_type=by_minisite")
    fun getGuokrHandpick(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<GuokrHandpickNews>

    @GET("article/{id}.json")
    fun getGuokrContent(@Path("id") id: Int): Call<GuokrHandpickContent>

}