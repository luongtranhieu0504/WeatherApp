package com.hieult.jetweatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "fav_tbl")
data class Favorite(
    @NotNull
    @PrimaryKey
    @ColumnInfo("city")
    val city: String,


    @ColumnInfo("country")
    val country: String


)
