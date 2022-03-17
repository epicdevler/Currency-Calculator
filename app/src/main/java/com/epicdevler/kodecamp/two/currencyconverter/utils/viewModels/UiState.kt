package com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels

data class UiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val loadingMsg: String? = null,
    val new :Boolean = false,
)
