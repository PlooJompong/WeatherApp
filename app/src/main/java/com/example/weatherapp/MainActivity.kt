package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.weatherapp.api.ApiService
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.City
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // Add viewBinding
    private lateinit var binding: ActivityMainBinding
    private val apiKey = "496590d7d8475f1ebd44ee0000855e47"
    private var cityName = ""

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionId = 100

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView (binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val tempCityName = intent.getStringExtra("City")
        if (tempCityName != null) {
            cityName = tempCityName
        }

        permission()

        // TODO - *ViewModel*
        // TODO - Extras: try to get current location with fusedLocationClient
        // TODO - Extras: add fragment
    }

    private fun permission() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                }
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show()
                    } else {
                        fetchWeatherData()
                        getCityWeatherData()
                    }
                }
            } else { // If local is disable. Launch settings
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // If permissions granted then check if location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Check if permissions is granted
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // If request permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            permissionId
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(applicationContext, "Permissions Granted", Toast.LENGTH_SHORT).show()
                permission()
            } else {
                Toast.makeText(applicationContext, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fetch weather data
    private fun fetchWeatherData() {
        ApiService.getApiInterface()?.getCityWeatherData("metric", cityName, apiKey)?.enqueue(object: Callback<City> {
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
                cityName = searchBar.text.toString()
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