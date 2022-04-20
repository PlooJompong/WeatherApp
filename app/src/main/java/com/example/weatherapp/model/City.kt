package com.example.weatherapp.model

import kotlin.collections.ArrayList

data class City(
    val weather: ArrayList<Weather>,
    val main: Main,
    val wind: Wind,
    val dt: Int,
    val name: String

/*
    @SerializedName("weather") val weather:ArrayList<Weather>,
    @SerializedName("main") val main:Main,
    @SerializedName("wind") val wind:Wind,
    @SerializedName("dt") val dt:Dt

 */
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



/*
 class Weather {
    @SerializedName("description") val description: String = ""
}

class Main {
    @SerializedName("temp") val temp: Double = 0.0
    @SerializedName("feels_like") val feels_like: Double = 0.0
    @SerializedName("temp_min") val temp_min: Double = 0.0
    @SerializedName("temp_max") val temp_max: Double = 0.0
    @SerializedName("humidity") val humidity: Int = 0
}

class Wind {
    @SerializedName("speed") val speed: Double = 0.0
}

class Dt {
    @SerializedName("dt") val dt: Int = 0
}

 */



