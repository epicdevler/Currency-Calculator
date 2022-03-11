package com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels

import androidx.lifecycle.MutableLiveData
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


    private val _currencyToConvertTo: MutableLiveData<String> = MutableLiveData()
    val currencyToConvertTo = _currencyToConvertTo

    fun updateCurrencyToConvertTo(currency: String) {
        with(_currencyToConvertTo) { value = currency }
    }


    private val _currencyToConvertFrom: MutableLiveData<String> = MutableLiveData()
    val currencyToConvertFrom = _currencyToConvertFrom
    fun updateCurrencyToConvertFrom(currency: String) {
        with(_currencyToConvertFrom) { value = currency }
    }

}