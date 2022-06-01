package com.example.weatherapp

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.api.ApiService
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.LatLon
import com.example.weatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CityViewModel
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "496590d7d8475f1ebd44ee0000855e47"
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val showWeatherFragment = ShowWeatherFragment()
    private val loadingFragment = LoadingFragment()

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CityViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView (binding.root)

        searchFunction()

    }

    private fun searchFunction() {
        binding.etSearchBar.setOnKeyListener(View.OnKeyListener {
                _, keyCode, event -> if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            if (binding.etSearchBar.text.isNotEmpty()) {
                viewModel.cityName = binding.etSearchBar.text.toString()
                fetLatLon()
                binding.etSearchBar.text.clear()
                hideSoftKeyboard()
            }
            return@OnKeyListener true
        }
            false
        })
    }

    private fun fetLatLon() {
        ApiService.getApiInterface()?.getLatLon(viewModel.cityName, "metric", apiKey)?.enqueue(object: Callback<LatLon> {
            override fun onResponse(call: Call<LatLon>, response: Response<LatLon>) {
                if (response.isSuccessful) {
                    saveLatLon(response.body())
                    fetchWeatherData()
                } else {
                    fragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, loadingFragment)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit()
                    }
                    Toast.makeText(this@MainActivity, "Invalid Location", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LatLon>, error: Throwable) {
                println(error)
            }
        })
    }

    private fun saveLatLon(body: LatLon?) {
        viewModel.cityName = body!!.name
        viewModel.lat = body.coord.lat
        viewModel.lon = body.coord.lon
        viewModel.low = body.main.temp_min
        viewModel.high = body.main.temp_max

    }

    private fun fetchWeatherData() {
        ApiService.getApiInterface()?.getData(viewModel.lat, viewModel.lon,"minutely, hourly, alerts","metric", apiKey)?.enqueue(object: Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful) {
                    if (!showWeatherFragment.isVisible) {
                        sendDataToFragment(response.body())
                        fragmentManager.beginTransaction().apply {
                            replace(R.id.fragmentContainer, showWeatherFragment)
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .commit()
                        }
                    } else {
                        sendDataToFragment(response.body())
                        fragmentManager.beginTransaction().apply {
                            remove(showWeatherFragment)
                                .commit()
                        }
                        fragmentManager.beginTransaction().apply {
                            add(R.id.fragmentContainer, showWeatherFragment)
                                .commit()
                        }
                    }

                } else {
                    fragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, loadingFragment)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }

            override fun onFailure(call: Call<WeatherData>, error: Throwable) {
                println(error)
            }
        })
    }

    private  fun timeStampToDate(timeStamp: Long): String {
        val dt = timeStamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .dayOfWeek
        }

        return dt.toString().lowercase().replaceFirstChar { it.uppercase() }
    }

    private  fun timeStampToTime(timeStamp: Long): String {
        val dt = timeStamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .hour
        }

        return dt.toString()
    }

    private fun sendDataToFragment(body: WeatherData?) {
        val bundle = Bundle()
        //Current
        bundle.putString("tvLocation", viewModel.cityName)
        bundle.putString("tvWeather", body!!.current.temp.toInt().toString())
        bundle.putString("tvMain", body.current.weather[0].main)
        bundle.putString("lowTemp", body.daily[0].temp.min.toInt().toString())
        bundle.putString("highTemp", body.daily[0].temp.max.toInt().toString())
        bundle.putString("tvFeelsLike", body.current.feels_like.toInt().toString())
        bundle.putString("tvHumidity", body.current.humidity.toString())
        bundle.putString("tvWindSpeed", body.current.wind_speed.toString())
        bundle.putString("icon", body.current.weather[0].icon)
        //Hourly
        //+1
        bundle.putString("tvTime", timeStampToTime(body.hourly[1].dt.toLong()))
        bundle.putString("tvTemp", body.hourly[1].temp.toInt().toString())
        bundle.putString("iconHourly", body.hourly[1].weather[0].icon)
        //+2
        bundle.putString("tvTime2", timeStampToTime(body.hourly[2].dt.toLong()))
        bundle.putString("tvTemp2", body.hourly[2].temp.toInt().toString())
        bundle.putString("iconHourly2", body.hourly[2].weather[0].icon)
        //+3
        bundle.putString("tvTime3", timeStampToTime(body.hourly[3].dt.toLong()))
        bundle.putString("tvTemp3", body.hourly[3].temp.toInt().toString())
        bundle.putString("iconHourly3", body.hourly[3].weather[0].icon)
        //+4
        bundle.putString("tvTime4", timeStampToTime(body.hourly[4].dt.toLong()))
        bundle.putString("tvTemp4", body.hourly[4].temp.toInt().toString())
        bundle.putString("iconHourly4", body.hourly[4].weather[0].icon)
        //Daily
        //+1
        bundle.putString("tvDay", timeStampToDate(body.daily[1].dt.toLong()))
        bundle.putString("tvDayLowTemp", body.daily[1].temp.min.toInt().toString())
        bundle.putString("tvDayHighTemp", body.daily[1].temp.max.toInt().toString())
        bundle.putString("iconDaily", body.daily[1].weather[0].icon)
        //+2
        bundle.putString("tvDay2", timeStampToDate(body.daily[2].dt.toLong()))
        bundle.putString("tvDayLowTemp2", body.daily[2].temp.min.toInt().toString())
        bundle.putString("tvDayHighTemp2", body.daily[2].temp.max.toInt().toString())
        bundle.putString("iconDaily2", body.daily[2].weather[0].icon)
        //+3
        bundle.putString("tvDay3", timeStampToDate(body.daily[3].dt.toLong()))
        bundle.putString("tvDayLowTemp3", body.daily[3].temp.min.toInt().toString())
        bundle.putString("tvDayHighTemp3", body.daily[3].temp.max.toInt().toString())
        bundle.putString("iconDaily3", body.daily[3].weather[0].icon)
        //+4
        bundle.putString("tvDay4", timeStampToDate(body.daily[4].dt.toLong()))
        bundle.putString("tvDayLowTemp4", body.daily[4].temp.min.toInt().toString())
        bundle.putString("tvDayHighTemp4", body.daily[4].temp.max.toInt().toString())
        bundle.putString("iconDaily4", body.daily[4].weather[0].icon)

        showWeatherFragment.arguments = bundle
    }

    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }
}

