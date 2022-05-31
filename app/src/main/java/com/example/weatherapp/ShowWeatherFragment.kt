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
            //Current
            val location = bundle.getString("tvLocation")
            val weather = bundle.getString("tvWeather")
            val main = bundle.getString("tvMain")
            val lowTemp = bundle.getString("lowTemp")
            val highTemp = bundle.getString("highTemp")
            val feelsLike = bundle.getString("tvFeelsLike")
            val humidity = bundle.getString("tvHumidity")
            val windSpeed = bundle.getString("tvWindSpeed")
            val icon = bundle.getString("icon")
            val picUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"
            Picasso.get()
                .load(picUrl)
                .into(fragmentBinding.ivCurrentBox)
            fragmentBinding.tvLocation.text = "$location"
            fragmentBinding.tvWeather.text = "$weather°"
            fragmentBinding.tvMain.text = main
            fragmentBinding.tvHighLowTemp.text = "L/H:  $lowTemp°/$highTemp°"
            fragmentBinding.tvFeelsLike.text = "Feels like:  $feelsLike°"
            fragmentBinding.tvHumidity.text = "Humunity:  $humidity%"
            fragmentBinding.tvWindSpeed.text = "Windspeed:  $windSpeed m/s"
            //Hourly
            //+1
            val dt = bundle.getString("tvTime")
            val tempHourly = bundle.getString("tvTemp")
            val iconHourly = bundle.getString("iconHourly")
            val picUrl1 = "https://openweathermap.org/img/wn/${iconHourly}@2x.png"
            Picasso.get()
                .load(picUrl1)
                .into(fragmentBinding.ivHourly)
            fragmentBinding.tvTime.text = "$dt:00"
            fragmentBinding.tvTemp.text = "$tempHourly°"
            //+2
            val dt2 = bundle.getString("tvTime2")
            val tempHourly2 = bundle.getString("tvTemp2")
            val iconHourly2 = bundle.getString("iconHourly2")
            val picUrl2 = "https://openweathermap.org/img/wn/${iconHourly2}@2x.png"
            Picasso.get()
                .load(picUrl2)
                .into(fragmentBinding.ivHourly2)
            fragmentBinding.tvTime2.text = "$dt2"
            fragmentBinding.tvTemp2.text = "$tempHourly2°"
            //+3
            val dt3 = bundle.getString("tvTime3")
            val tempHourly3 = bundle.getString("tvTemp3")
            val iconHourly3 = bundle.getString("iconHourly3")
            val picUrl3 = "https://openweathermap.org/img/wn/${iconHourly3}@2x.png"
            Picasso.get()
                .load(picUrl3)
                .into(fragmentBinding.ivHourly3)
            fragmentBinding.tvTime3.text = "$dt3:00"
            fragmentBinding.tvTemp3.text = "$tempHourly3°"
            //+4
            val dt4 = bundle.getString("tvTime4")
            val tempHourly4 = bundle.getString("tvTemp4")
            val iconHourly4 = bundle.getString("iconHourly4")
            val picUrl4 = "https://openweathermap.org/img/wn/${iconHourly4}@2x.png"
            Picasso.get()
                .load(picUrl4)
                .into(fragmentBinding.ivHourly4)
            fragmentBinding.tvTime4.text = "$dt4:00"
            fragmentBinding.tvTemp4.text = "$tempHourly4°"



        }
    }
}