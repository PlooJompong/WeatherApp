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



