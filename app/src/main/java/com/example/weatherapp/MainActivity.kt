package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.ApiInterface
import com.example.weatherapp.model.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // Add viewBinding
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "496590d7d8475f1ebd44ee0000855e47"
    private var cityName = ""
    private var tempCityName = "Stockholm"

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView (binding.root)

        /* API
        "https://api.openweathermap.org/data/2.5/weather?units=metric&q=stockholm&appid=496590d7d8475f1ebd44ee0000855e47"
        */

        fetchCurrentLocation(tempCityName)

        // TODO: Search Function

    }

    private fun searchCity() {
        val searchBar = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchBar)
        searchBar.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                cityName = query
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun fetchCurrentLocation(cityName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            //.baseUrl("https://api.openweathermap.org/data/2.5/weather?units=metric&q=${cityName}&appid=${apiKey}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Connector
        val apiService = retrofit.create(ApiInterface::class.java)
        val call = apiService.getInfo("metric", cityName, apiKey )

        call.enqueue(object: Callback<City> {
            // Quickfix ??
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<City>, response: Response<City>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    binding.currentWeather.text = weather!!.main.temp.toInt().toString() + "°"
                    binding.currentLocation.text = weather!!.name
                    binding.description.text = weather.weather[0].main
                    binding.highTemp.text =  "H: " + weather.main.temp_max.toInt().toString() + "°"
                    binding.lowTemp.text = "L: " + weather.main.temp_min.toInt().toString() + "°"
                    binding.feelsLike.text = weather.main.feels_like.toInt().toString() + "°"
                    binding.humidity.text = weather.main.humidity.toString() + "%"
                    binding.windSpeed.text = weather.wind.speed.toInt().toString() + " m/s"

                    /* Debug
                    Toast.makeText(this@MainActivity, weather!!.name, Toast.LENGTH_SHORT).show()
                    println(weather.main.temp.toString() + "°")
                    println(weather.main.temp.toInt().toString() + "°")
                     */
                }
            }
            override fun onFailure(call: Call<City>, error: Throwable) {
                println(error)
            }
        })
    }
}
