package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView (R.layout.activity_main)

        /* API
        "https://api.openweathermap.org/data/2.5/weather?units=metric&q=stockholm&appid=496590d7d8475f1ebd44ee0000855e47"
        */

        fetchCurrentLocation()

    }


    private fun fetchCurrentLocation() {

    }
}
