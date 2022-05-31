package com.example.weatherapp.model

data class WeatherData(
    val current: Current,
    val hourly: ArrayList<Hourly>,
    val daily: ArrayList<Daily>,
) {
     class Current(val weather: ArrayList<Weather>) {
         val temp: Double = 0.00
         val feels_like: Double = 0.00
         val humidity: Int = 0
         val wind_speed: Double = 0.00

         class Weather {
             val main: String = ""
             val icon: String = ""
         }
     }

    class Hourly(val weather: ArrayList<Weather>) {
        val dt: Int = 0
        val temp: Double = 0.00

        class Weather {
            val icon: String = ""
        }
    }

    class Daily(val weather: ArrayList<Weather>, val temp: Temp) {
        val dt: Int = 0

        class Temp {
            val min: Double = 0.00
            val max: Double = 0.00
        }

        class Weather {
            val icon: String = ""
        }
    }

}

