package com.example.weatherapp.Api

import com.example.weatherapp.Model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("q")
        cityName:String,
        @Query("appid")
        appId: String,
        @Query("units")
        units: String= "metric"
    ): Response<WeatherResponse>
}