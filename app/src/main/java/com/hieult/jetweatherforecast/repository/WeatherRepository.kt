package com.hieult.jetweatherforecast.repository

import com.hieult.jetweatherforecast.data.DataOrException
import com.hieult.jetweatherforecast.model.Weather
import com.hieult.jetweatherforecast.model.WeatherObject
import com.hieult.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery : String)
    : DataOrException<Weather,Boolean,Exception> {
        val response = try {
            api.getWeather(cityQuery)
        }catch (e : Exception){
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}