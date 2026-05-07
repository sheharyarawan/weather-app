package com.example.weatherapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Model.WeatherResponse
import com.example.weatherapp.Repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class weatherViewModel(app: Application, val repo: Repository): AndroidViewModel(app) {

    val weather: MutableLiveData<WeatherResponse> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun getWeather(city: String){
        viewModelScope.launch {
            val response= repo.getWeather(city)
            handleWeatherResponse(response)
        }
    }

    fun handleWeatherResponse(response: Response<WeatherResponse>){
        if(response.isSuccessful){
            response.body()?.let{newResponse->
                weather.postValue(newResponse)
            }
        }
        else{
            error.postValue("Error: ${response.message()}")
        }
    }
}