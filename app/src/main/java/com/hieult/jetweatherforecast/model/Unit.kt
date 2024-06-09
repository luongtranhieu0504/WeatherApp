package com.hieult.jetweatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "setting_tbl")
data class Unit(
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "unit")
    val unit : String)
