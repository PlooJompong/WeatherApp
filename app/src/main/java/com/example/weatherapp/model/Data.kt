package com.example.weatherapp.model

data class Data(
    //val weather: ArrayList<Weather>,
    val current: Current,
    val daily: ArrayList<Daily>
) {
    /*
    class Weather {
        val main: String = ""
    }

     */

     class Current {
         val temp: Double = 0.00
         val feels_like: Double = 0.00
         val humidity: Int = 0
         val wind_speed: Double = 0.00
     }

    class Daily {
        val dt: Int = 0
    }
}

