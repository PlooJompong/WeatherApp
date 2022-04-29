package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.api.ApiService
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    // viewBinding
    private lateinit var binding: ActivityMainBinding

    // viewModel
    private lateinit var viewModel: CityNameViewModel

    private val apiKey = "496590d7d8475f1ebd44ee0000855e47"

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView (binding.root)

        // viewModel
        viewModel = ViewModelProvider(this)[CityNameViewModel::class.java]

        // Make sure getStringExtra runs only once
        if (viewModel.cityName.isEmpty()) {
            getStringExtra()
        }

        fetchWeatherData()
        getCityWeatherData()
    }

    // Get string extra
    private fun getStringExtra() {
        val tempCityName = intent.getStringExtra("City")
        if (tempCityName != null) {
            viewModel.cityName = tempCityName
        }
    }

    // Fetch weather data
    private fun fetchWeatherData() {
        ApiService.getApiInterface()?.getCityWeatherData("metric", viewModel.cityName, apiKey)?.enqueue(object: Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                if (response.isSuccessful) {
                    setDataOnView(response.body())
                } else {
                    binding.root.visibility = GONE
                    val intent = Intent(this@MainActivity, StartCity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Invalid City Name", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<City>, error: Throwable) {
                println(error)
                Toast.makeText(applicationContext, "Error" ,Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Get city weather data
    private fun getCityWeatherData() {
        val searchBar = findViewById<EditText>(R.id.etSearchBar)
        searchBar.setOnKeyListener(View.OnKeyListener {
                _, keyCode, event -> if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            if (searchBar.text.isNotEmpty()) {
                viewModel.cityName = searchBar.text.toString()
                fetchWeatherData()
                hideSoftKeyboard()
                searchBar.text.clear()
            }
            return@OnKeyListener true
        }
            false
        })
    }

    // Hide keyboard
    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }

    // Set data on view
    @SuppressLint("SetTextI18n")
    private fun setDataOnView(body: City?) {
        binding.tvWeather.text = body!!.main.temp.toInt().toString() + "°"
        binding.tvLocation.text = body!!.name
        binding.tvDescription.text = body.weather[0].main
        binding.tvHighTemp.text =  "H: " + body.main.temp_max.toInt().toString() + "°"
        binding.tvLowTemp.text = "L: " + body.main.temp_min.toInt().toString() + "°"
        binding.tvFeelsLike.text = body.main.feels_like.toInt().toString() + "°"
        binding.tvHumidity.text = body.main.humidity.toString() + "%"
        binding.tvWindSpeed.text = body.wind.speed.toInt().toString() + " m/s"
    }
}