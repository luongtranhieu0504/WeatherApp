package com.hieult.jetweatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieult.jetweatherforecast.model.City
import com.hieult.jetweatherforecast.model.Favorite
import com.hieult.jetweatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * from fav_tbl")
    fun getAllFavorite(): Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getByIdFavorite(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    //Unit
    @Query("SELECT * from setting_tbl")
    fun getUnit(): Flow<List<Unit>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)
    @Query("DELETE from setting_tbl")
    suspend fun deleteAllUnit()
    @Delete
    suspend fun deleteUnit(unit: Unit)

}