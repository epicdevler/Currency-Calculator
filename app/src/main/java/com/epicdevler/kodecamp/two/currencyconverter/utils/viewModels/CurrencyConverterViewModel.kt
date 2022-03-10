package com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    @Named("baseURL")
    private val baseURL: String
) : ViewModel() {

    private val _uiState = Channel<String>()
    val uiState = _uiState.receiveAsFlow()

    fun test() {
        viewModelScope.launch {
            _uiState.send("Hello World")
        }
    }

}