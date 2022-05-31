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
            fragmentBinding.tvWindSpeed.text = "Wind speed:  $windSpeed m/s"
            //Hourly
            //+1
            val dtHourly = bundle.getString("tvTime")
            val tempHourly = bundle.getString("tvTemp")
            val iconHourly = bundle.getString("iconHourly")
            val picUrl1 = "https://openweathermap.org/img/wn/${iconHourly}@2x.png"
            Picasso.get()
                .load(picUrl1)
                .into(fragmentBinding.ivHourly)
            fragmentBinding.tvTime.text = "$dtHourly:00"
            fragmentBinding.tvTemp.text = "$tempHourly°"
            //+2
            val dtHourly2 = bundle.getString("tvTime2")
            val tempHourly2 = bundle.getString("tvTemp2")
            val iconHourly2 = bundle.getString("iconHourly2")
            val picUrl2 = "https://openweathermap.org/img/wn/${iconHourly2}@2x.png"
            Picasso.get()
                .load(picUrl2)
                .into(fragmentBinding.ivHourly2)
            fragmentBinding.tvTime2.text = "$dtHourly2:00"
            fragmentBinding.tvTemp2.text = "$tempHourly2°"
            //+3
            val dtHourly3 = bundle.getString("tvTime3")
            val tempHourly3 = bundle.getString("tvTemp3")
            val iconHourly3 = bundle.getString("iconHourly3")
            val picUrl3 = "https://openweathermap.org/img/wn/${iconHourly3}@2x.png"
            Picasso.get()
                .load(picUrl3)
                .into(fragmentBinding.ivHourly3)
            fragmentBinding.tvTime3.text = "$dtHourly3:00"
            fragmentBinding.tvTemp3.text = "$tempHourly3°"
            //+4
            val dtHourly4 = bundle.getString("tvTime4")
            val tempHourly4 = bundle.getString("tvTemp4")
            val iconHourly4 = bundle.getString("iconHourly4")
            val picUrl4 = "https://openweathermap.org/img/wn/${iconHourly4}@2x.png"
            Picasso.get()
                .load(picUrl4)
                .into(fragmentBinding.ivHourly4)
            fragmentBinding.tvTime4.text = "$dtHourly4:00"
            fragmentBinding.tvTemp4.text = "$tempHourly4°"
            //Daily
            //+1
            val dtDaily = bundle.getString("tvDay")
            val tempLowDaily = bundle.getString("tvDayLowTemp")
            val tempHighDaily = bundle.getString("tvDayHighTemp")
            val iconDaily = bundle.getString("iconDaily")
            val picUrl5 = "https://openweathermap.org/img/wn/${iconDaily}@2x.png"
            Picasso.get()
                .load(picUrl5)
                .into(fragmentBinding.ivDaily)
            fragmentBinding.tvDay.text = "$dtDaily"
            fragmentBinding.tvDayTemp.text = "$tempLowDaily°/$tempHighDaily°"
            //+2
            val dtDaily2 = bundle.getString("tvDay2")
            val tempLowDaily2 = bundle.getString("tvDayLowTemp2")
            val tempHighDaily2 = bundle.getString("tvDayHighTemp2")
            val iconDaily2 = bundle.getString("iconDaily2")
            val picUrl6 = "https://openweathermap.org/img/wn/${iconDaily2}@2x.png"
            Picasso.get()
                .load(picUrl6)
                .into(fragmentBinding.ivDaily2)
            fragmentBinding.tvDay2.text = "$dtDaily2"
            fragmentBinding.tvDayTemp2.text = "$tempLowDaily2°/$tempHighDaily2°"
            //+3
            val dtDaily3 = bundle.getString("tvDay3")
            val tempLowDaily3 = bundle.getString("tvDayLowTemp3")
            val tempHighDaily3 = bundle.getString("tvDayHighTemp3")
            val iconDaily3 = bundle.getString("iconDaily3")
            val picUrl7 = "https://openweathermap.org/img/wn/${iconDaily3}@2x.png"
            Picasso.get()
                .load(picUrl7)
                .into(fragmentBinding.ivDaily3)
            fragmentBinding.tvDay3.text = "$dtDaily3"
            fragmentBinding.tvDayTemp3.text = "$tempLowDaily3°/$tempHighDaily3°"
            //+4
            val dtDaily4 = bundle.getString("tvDay4")
            val tempLowDaily4 = bundle.getString("tvDayLowTemp4")
            val tempHighDaily4 = bundle.getString("tvDayHighTemp4")
            val iconDaily4 = bundle.getString("iconDaily4")
            val picUrl8 = "https://openweathermap.org/img/wn/${iconDaily4}@2x.png"
            Picasso.get()
                .load(picUrl8)
                .into(fragmentBinding.ivDaily4)
            fragmentBinding.tvDay4.text = "$dtDaily4"
            fragmentBinding.tvDayTemp4.text = "$tempLowDaily4°/$tempHighDaily4°"
        }
    }
}