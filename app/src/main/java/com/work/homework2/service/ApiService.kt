package com.work.homework2.service

import com.work.homework2.data.BeerModelsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("beers")
    fun getBeers(
        @Query("page") page: Int = 1
    ): Call<List<BeerModelsItem>>

    @GET("beers/{id}")
    fun getContext(@Path("id") id: Int): Call<List<BeerModelsItem>>
}