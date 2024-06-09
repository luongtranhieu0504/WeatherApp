package com.hieult.jetweatherforecast.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hieult.jetweatherforecast.R
import com.hieult.jetweatherforecast.model.WeatherItem
import com.hieult.jetweatherforecast.utils.formatDate
import com.hieult.jetweatherforecast.utils.formatDateTime
import com.hieult.jetweatherforecast.utils.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp))) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = formatDate(weather.dt)
                .split(",")[0],
                modifier = Modifier.padding(start = 5.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(3.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = weather.weather[0].description,
                    style = MaterialTheme.typography.caption)
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(0.7f),
                    fontWeight = FontWeight.Bold
                )
                ){
                    append(formatDecimals(weather.temp.max) + "°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold
                )
                ){
                    append(formatDecimals(weather.temp.min) + "°")
                }
            })
        }

    }

}

@Composable
fun SunsetAndSunriseRow(weather: WeatherItem) {
    Row (modifier = Modifier
        .padding(top = 15.dp, bottom = 6.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunset),
                contentDescription = "icon sunset",
                modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.caption)

        }
        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.caption)
            Icon(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))

        }


    }

}

@Composable
fun DrawSingleLine(){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(color = Color.LightGray,
            start = Offset(0f,0f), end = Offset(size.width,0f)
        )

    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row (modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "icon humidity",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption)

        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption)

        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "icon pressure",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.speed} mph",
                style = MaterialTheme.typography.caption)

        }

    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(data = imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier.size(80.dp))
}
