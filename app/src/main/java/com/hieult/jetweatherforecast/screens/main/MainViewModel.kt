package com.hieult.jetweatherforecast.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieult.jetweatherforecast.data.DataOrException
import com.hieult.jetweatherforecast.model.City
import com.hieult.jetweatherforecast.model.Weather
import com.hieult.jetweatherforecast.model.WeatherObject
import com.hieult.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository)
    :ViewModel( ){
        suspend fun getWeather(city: String): DataOrException<Weather,Boolean,Exception>{
            return repository.getWeather(cityQuery = city)
        }
}