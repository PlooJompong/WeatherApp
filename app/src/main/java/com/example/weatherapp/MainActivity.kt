package com.example.weatherapp

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.api.ApiService
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CityNameViewModel
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "496590d7d8475f1ebd44ee0000855e47"
    private val fragmentManager: FragmentManager = supportFragmentManager
    //private val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
    private val showWeatherFragment = ShowWeatherFragment()
    private val loadingFragment = LoadingFragment()

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CityNameViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView (binding.root)

        searchFunction()

        //TODO - Reload data to fragment

    }

    private fun searchFunction() {
        binding.etSearchBar.setOnKeyListener(View.OnKeyListener {
                _, keyCode, event -> if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            if (binding.etSearchBar.text.isNotEmpty()) {
                viewModel.cityName = binding.etSearchBar.text.toString()
                fetchWeatherData()
                binding.etSearchBar.text.clear()
                hideSoftKeyboard()
            }
            return@OnKeyListener true
        }
            false
        })
    }

    private fun fetchWeatherData() {
        ApiService.getApiInterface()?.getCityWeatherData("metric", viewModel.cityName, apiKey)?.enqueue(object: Callback<City> {
            override fun onResponse(call: Call<City>, response: Response<City>) {
                if (response.isSuccessful) {
                    setDataOnView(response.body())
                    fragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, showWeatherFragment)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit()
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

            override fun onFailure(call: Call<City>, error: Throwable) {
                println(error)
            }
        })
    }

    private fun setDataOnView(body: City?) {
        val bundle = Bundle()
        bundle.putString("tvDescription", body!!.weather[0].main)
        bundle.putString("tvLocation", body.name)
        bundle.putString("tvWeather", body.main.temp.toInt().toString())
        bundle.putString("tvHighTemp", body.main.temp_max.toInt().toString())
        bundle.putString("tvLowTemp", body.main.temp_min.toInt().toString())
        bundle.putString("tvFeelsLike", body.main.feels_like.toInt().toString())
        bundle.putString("tvHumidity", body.main.humidity.toString())
        bundle.putString("tvWindSpeed", body.wind.speed.toInt().toString())
        showWeatherFragment.arguments = bundle
    }

    // Hide softkeyboard
    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }

        /*
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

    // Hide softkeyboard
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

         */
}
