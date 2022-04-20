package com.example.weatherapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun getInfo(@Query("units") unit: String,
                @Query("q") q: String,
                @Query("appid") appId: String
    ): Call<City>
}