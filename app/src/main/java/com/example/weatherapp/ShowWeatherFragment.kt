package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentShowWeatherBinding
import com.squareup.picasso.Picasso

class ShowWeatherFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentShowWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentShowWeatherBinding.inflate(inflater, container, false)

        setDataOnView()

        return fragmentBinding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setDataOnView() {
        val bundle = arguments

        if (bundle != null) {
            val location = bundle.getString("tvLocation")
            val weather = bundle.getString("tvWeather")
            val main = bundle.getString("tvMain")
            //High/Low temp
            val lowTemp = bundle.getString("lowTemp")
            val highTemp = bundle.getString("highTemp")

            val feelsLike = bundle.getString("tvFeelsLike")
            val humidity = bundle.getString("tvHumidity")
            val windSpeed = bundle.getString("tvWindSpeed")

            val icon = bundle.getString("icon")
            val picUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"

            Picasso.get()
                .load(picUrl)
                .into(fragmentBinding.imageView)
            println(icon)

            fragmentBinding.tvLocation.text = "$location"
            fragmentBinding.tvWeather.text = "$weather°"
            fragmentBinding.tvMain.text = main
            //High/Low temp
            fragmentBinding.tvHighLowTemp.text = "H/L:  $lowTemp°/$highTemp°"
            fragmentBinding.tvFeelsLike.text = "Feels ike:  $feelsLike°"
            fragmentBinding.tvHumidity.text = "Humunity:  $humidity%"
            fragmentBinding.tvWindSpeed.text = "Windspeed:  $windSpeed m/s"
/*
            fragmentBinding.tvLowTemp.text = "L: $lowTemp:00"

 */
        }
    }
}