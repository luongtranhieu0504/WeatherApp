package com.hieult.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hieult.jetweatherforecast.data.DataOrException
import com.hieult.jetweatherforecast.model.Weather
import com.hieult.jetweatherforecast.model.WeatherItem
import com.hieult.jetweatherforecast.navigation.WeatherScreens
import com.hieult.jetweatherforecast.utils.formatDate
import com.hieult.jetweatherforecast.utils.formatDecimals
import com.hieult.jetweatherforecast.widgets.DrawSingleLine
import com.hieult.jetweatherforecast.widgets.HumidityWindPressureRow
import com.hieult.jetweatherforecast.widgets.SunsetAndSunriseRow
import com.hieult.jetweatherforecast.widgets.WeatherAppBar
import com.hieult.jetweatherforecast.widgets.WeatherDetailRow
import com.hieult.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
){
    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeather(city = city.toString())
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    }else if (weatherData.data.toString() != null) {
        MainScaffold(weather = weatherData.data!!,navController)

    }
}


@SuppressLint("ProduceStateDoesNotAssignValue", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    androidx.compose.material.Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}",
            icon = Icons.Default.ArrowBack,
            isMainScreen = true,
            navController = navController,
            onAddActionClick = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp)
    }) {
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        
        Text(text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)) {
            
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(weatherItem.temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
            }
        }

        HumidityWindPressureRow(weather = data.list[0])
        DrawSingleLine()
        SunsetAndSunriseRow(weather = data.list[0])
        Text(text = "This Week", style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold)
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)) {
                items(items = data.list) { item: WeatherItem ->  
                    WeatherDetailRow(weather = item)
                }

            }

        }

    }
}

