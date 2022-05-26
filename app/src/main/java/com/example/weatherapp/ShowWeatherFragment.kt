package com.example.weatherapp

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

        fragmentBinding.tvLocation.text = "Hi"

        return fragmentBinding.root
    }
}