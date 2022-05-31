package com.example.weatherapp.model

data class Data(
    val current: Current,
    val hourly: ArrayList<Hourly>,
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

    class Hourly(val weather: ArrayList<Weather>) {
        val dt: Int = 0

        class Weather {
            val id: Int = 0
            val main: String = ""
            val description: String = ""
            val icon: String = ""
        }
    }

    class Daily(val weather: ArrayList<Hourly.Weather>) {
        val dt: Int = 0

        class Weather {
            val id: Int = 0
            val main: String = ""
            val description: String = ""
            val icon: String = ""
        }
    }
}

