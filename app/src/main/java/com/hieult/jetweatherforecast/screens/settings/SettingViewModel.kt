package com.hieult.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieult.jetweatherforecast.model.Unit
import com.hieult.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val repository: WeatherDbRepository)
    : ViewModel(){

        private val _setList = MutableStateFlow<List<Unit>>(emptyList())
        val setList = _setList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnit().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()){
                        Log.d("Set", ": Empty list")
                    }else {
                        _setList.value = listOfUnits
                    }
                }
        }
    }

    fun insertUnit(unit: Unit)
            = viewModelScope.launch { repository.insertUnit(unit) }
    fun updateUnit(unit: Unit)
            = viewModelScope.launch { repository.updateUnit(unit) }
    fun removeUnit(unit: Unit)
            = viewModelScope.launch { repository.deleteUnit(unit) }
}