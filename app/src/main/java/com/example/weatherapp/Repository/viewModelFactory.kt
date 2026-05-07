package com.example.weatherapp.Repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.weatherViewModel

class viewModelFactory(val app: Application, val repo: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return weatherViewModel(app, repo) as T
    }
}