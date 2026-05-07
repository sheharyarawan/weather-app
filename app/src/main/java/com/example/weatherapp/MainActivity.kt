package com.example.weatherapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherapp.Model.WeatherResponse
import com.example.weatherapp.Repository.Repository
import com.example.weatherapp.Repository.viewModelFactory
import com.example.weatherapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: weatherViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var condition: String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpViewModel()

        viewModel.getWeather("Hafizabad")

        viewModel.weather.observe(this){response->
            response?.let{
                updateUI(it)
            }
        }

        searchCity()
    }

    private fun searchCity() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getWeather(it)
                    searchView.clearFocus() // hide keyboard
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    fun setUpViewModel(){
        val repo= Repository()
        val factory= viewModelFactory(application, repo)
        viewModel= ViewModelProvider(this, factory)[weatherViewModel::class.java]
    }

    private fun updateUI(weather: WeatherResponse) {
        condition=weather.weather[0].main
        binding.cityName.text = weather.name
        binding.temperature.text = "${weather.main.temp} °C"
        binding.weather.text = condition
        binding.maxTemp.text = "Max: ${weather.main.temp_max} °C"
        binding.minTemp.text = "Min: ${weather.main.temp_min} °C"
        binding.humidity.text = "${weather.main.humidity}%"
        binding.windspeed.text = "${weather.wind.speed} m/s"
        binding.sunrise.text= "${weather.sys.sunrise}"
        binding.sunset.text= "${weather.sys.sunset}"
        binding.seaLevel.text= "${weather.main.sea_level} hPa"
        binding.condition.text=condition
        binding.day.text=dayName(System.currentTimeMillis())
        binding.date.text= date()

        changeBgUi()

    }

    fun dayName(timeStamp: Long): String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }

    fun date(): String{
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }


    fun changeBgUi(){
        when(condition){

            "Clear Sky", "Sunny", "Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView2.setAnimation(R.raw.sun)
            }

            "Party Cloudy", "Clouds", "Overcast", "Mist", "Foggy"->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView2.setAnimation(R.raw.cloud)
            }

            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView2.setAnimation(R.raw.rain)
            }

            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView2.setAnimation(R.raw.snow)
            }
        }
        binding.lottieAnimationView2.playAnimation()
    }
}