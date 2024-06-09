package com.hieult.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hieult.jetweatherforecast.model.Favorite
import com.hieult.jetweatherforecast.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = true)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun WeatherDao() : WeatherDao
}