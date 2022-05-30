package com.example.weatherapp.api

import com.example.weatherapp.model.City
import com.example.weatherapp.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getLatLon(@Query("q") q: String,
                  @Query("appid") appId: String
    ): Call<City>

    @GET("onecall")
    fun getData(@Query("lat") lat: Double,
                @Query("lon") lon: Double,
                @Query("exclude") exclude: String,
                @Query("units") metric: String,
                @Query("appid") appId: String
    ): Call<Data>

}