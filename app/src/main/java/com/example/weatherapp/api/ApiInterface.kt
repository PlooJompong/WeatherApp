package com.example.weatherapp.api

import com.example.weatherapp.model.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getCityWeatherData(@Query("units") units: String,
                           @Query("q") q: String,
                           @Query("appid") appId: String
    ): Call<City>
}