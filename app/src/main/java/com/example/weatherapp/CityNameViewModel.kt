package com.example.weatherapp

import androidx.lifecycle.ViewModel

class CityNameViewModel() : ViewModel() {
    var cityName = ""
    var lat: Double = 0.00
    var lon: Double = 0.00
}