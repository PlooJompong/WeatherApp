package com.example.weatherapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StartCity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_city)

        getCity()
    }

    private fun getCity() {
        val searchCity = findViewById<EditText>(R.id.etSearchCity)
        // Search City
        searchCity.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (searchCity.text.isNotEmpty()) {
                    val name = searchCity.text.toString()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("City", name)
                    }
                    startActivity(intent)
                    hideSoftKeyboard()
                    searchCity.text.clear()
                }
            }
            false
        }
    }

    // Hide keyboard
    private fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }
}
