package com.example.weatherapp.Repository

import com.example.weatherapp.Api.retrofitInstance
import com.example.weatherapp.Api.weatherApi
import com.example.weatherapp.Constants.Constants
import com.example.weatherapp.Model.WeatherResponse
import retrofit2.Response

class Repository() {

    suspend fun getWeather(city: String): Response<WeatherResponse>{
        return retrofitInstance.api.getWeatherData(city, Constants.Companion.API_KEY)
    }

}