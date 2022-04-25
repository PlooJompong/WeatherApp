package com.example.weatherapp.model

import kotlin.collections.ArrayList

data class City(
    val weather: ArrayList<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String

) {
    class Weather {
        val main: String = ""
    }

    class Main {
        val temp: Double = 0.0
        val feels_like: Double = 0.0
        val temp_min: Double = 0.0
        val temp_max: Double = 0.0
        val humidity: Int = 0
    }

    class Wind {
        val speed: Double = 0.0
    }
}



