package com.example.weatherapp.model


data class LatLon(
    val coord: Coord,
    val main: Main,
    val name: String

) {
    class Coord {
        val lon: Double = 0.00
        val lat: Double = 0.00
    }

    class Main {
        val temp_min: Double = 0.0
        val temp_max: Double = 0.0
    }
}
/*
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
 */



