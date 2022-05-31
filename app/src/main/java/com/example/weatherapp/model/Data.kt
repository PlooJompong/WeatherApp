package com.example.weatherapp.model

data class Data(
    val current: Current,
    val daily: ArrayList<Daily>
) {
     class Current(val weather: ArrayList<Weather>) {
         val temp: Double = 0.00
         val feels_like: Double = 0.00
         val humidity: Int = 0
         val wind_speed: Double = 0.00

         class Weather {
             val id: Int = 0
             val main: String = ""
             val description: String = ""
             val icon: String = ""
         }
     }

    class Daily {
        val dt: Int = 0
    }
}

