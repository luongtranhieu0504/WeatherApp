package com.hieult.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hieult.jetweatherforecast.screens.about.AboutScreen
import com.hieult.jetweatherforecast.screens.favorites.FavoriteScreen
import com.hieult.jetweatherforecast.screens.main.MainScreen
import com.hieult.jetweatherforecast.screens.main.MainViewModel
import com.hieult.jetweatherforecast.screens.search.SearchScreen
import com.hieult.jetweatherforecast.screens.settings.SettingScreen
import com.hieult.jetweatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                })){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController,mainViewModel,
                    city = city)
            }

        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name){
            FavoriteScreen(navController = navController)
        }
        composable(WeatherScreens.SettingScreen.name){
            SettingScreen(navController = navController)
        }
    }
}