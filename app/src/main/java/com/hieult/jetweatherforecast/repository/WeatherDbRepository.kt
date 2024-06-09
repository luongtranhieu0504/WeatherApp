package com.hieult.jetweatherforecast.repository

import com.hieult.jetweatherforecast.data.WeatherDao
import com.hieult.jetweatherforecast.model.Favorite
import com.hieult.jetweatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorite() : Flow<List<Favorite>> = weatherDao.getAllFavorite()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorite()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city: String) : Favorite = weatherDao.getByIdFavorite(city)
    //Unit
    fun getUnit() : Flow<List<Unit>> = weatherDao.getUnit()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnit() = weatherDao.deleteAllUnit()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
}