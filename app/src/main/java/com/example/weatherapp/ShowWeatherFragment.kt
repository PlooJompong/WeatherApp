package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentShowWeatherBinding

class ShowWeatherFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentShowWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentShowWeatherBinding.inflate(inflater, container, false)

        getData()

        return fragmentBinding.root
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        val bundle = arguments

        if (bundle != null) {
            val description = bundle.getString("tvDescription")
            val location = bundle.getString("tvLocation")
            val weather = bundle.getString("tvWeather")
            val highTemp = bundle.getString("tvHighTemp")
            val lowTemp = bundle.getString("tvLowTemp")
            val feelsLike = bundle.getString("tvFeelsLike")
            val humidity = bundle.getString("tvHumidity")
            val windSpeed = bundle.getString("tvWindSpeed")

            fragmentBinding.tvDescription.text = description
            fragmentBinding.tvLocation.text = "$location"
            fragmentBinding.tvWeather.text = "$weather°"
            fragmentBinding.tvHighTemp.text = "H: $highTemp°"
            fragmentBinding.tvLowTemp.text = "L: $lowTemp°"
            fragmentBinding.tvFeelsLike.text = "$feelsLike°"
            fragmentBinding.tvHumidity.text = "$humidity%"
            fragmentBinding.tvWindSpeed.text = "$windSpeed m/s"
        }
    }
}