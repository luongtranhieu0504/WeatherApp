package com.hieult.jetweatherforecast.di

import android.content.Context
import androidx.room.Room
import com.hieult.jetweatherforecast.data.WeatherDao
import com.hieult.jetweatherforecast.data.WeatherDatabase
import com.hieult.jetweatherforecast.network.WeatherApi
import com.hieult.jetweatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) : WeatherDao
    = weatherDatabase.WeatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : WeatherDatabase
    = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideOpenWeatherApi() : WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}