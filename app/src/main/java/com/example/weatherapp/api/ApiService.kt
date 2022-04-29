package com.example.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private var retrofit: Retrofit? = null
    private var baseUrl = "https://api.openweathermap.org/data/2.5/"

    fun getApiInterface(): ApiInterface? {
        if (retrofit==null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiInterface::class.java)
    }
}