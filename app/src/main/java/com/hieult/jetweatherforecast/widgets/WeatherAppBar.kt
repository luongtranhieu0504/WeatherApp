package com.hieult.jetweatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hieult.jetweatherforecast.R
import com.hieult.jetweatherforecast.model.Favorite
import com.hieult.jetweatherforecast.navigation.WeatherScreens
import com.hieult.jetweatherforecast.screens.favorites.FavoriteViewModel


@Composable
fun WeatherAppBar(
    title: String = "",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    elevation: Dp = 0.dp,
    onAddActionClick: () -> Unit = {},
    onButtonClick: () -> Unit = {}
){
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value){
        ShowSettingDropMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        title = {
                Text(text = title,
                    color = MaterialTheme.colors.onSecondary,
                    style = TextStyle(fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp)
                )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClick.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Icon Search"
                    )
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Icon more"
                    )
                }
            }else {
                Box {

                }
            }

        },
        navigationIcon = {
            if (icon != null && !isMainScreen) {
                Icon(imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClick.invoke()
                    })
            }
            if (isMainScreen){
                val isAlreadyFavList = favoriteViewModel
                    .favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }

                if (isAlreadyFavList.isNullOrEmpty()){
                    Icon(imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "favorite Icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = dataList[0],
                                        country = dataList[1],
                                    )). run {
                                        showIt.value = true
                                }
                            }
                    )
                }else {
                    showIt.value = false
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "fav",
                        tint = Color.Red.copy(0.6f))
                }
                ShowToast(context = context,showIt)

            }
        },
        elevation = elevation,
        backgroundColor = Color.Transparent)



}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value){
        Toast.makeText(context,"Added to Favorites",
            Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun ShowSettingDropMenu(showDialog: MutableState<Boolean>,
                        navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("About","Favorite","Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)) {
            items.forEachIndexed { index, text -> 
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(imageVector = when(text){
                        "About" -> Icons.Default.Info
                        "Favorite" -> Icons.Default.Favorite
                        else -> Icons.Default.Settings },
                        contentDescription = null,
                        tint = Color.LightGray)
                    Text(text = text, modifier = Modifier.clickable {
                        navController.navigate(
                        when(text){
                            "About" -> WeatherScreens.AboutScreen.name
                            "Favorite" -> WeatherScreens.FavoriteScreen.name
                            else -> WeatherScreens.SettingScreen.name})},
                        fontWeight = FontWeight.W300)
                }
            }

        }

    }

}
