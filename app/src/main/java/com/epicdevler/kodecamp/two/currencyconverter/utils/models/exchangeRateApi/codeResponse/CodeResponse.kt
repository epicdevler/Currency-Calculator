package com.epicdevler.kodecamp.two.currencyconverter.utils.models.exchangeRateApi.codeResponse

data class CodeResponse(
    val documentation: String,
    val result: String,
    val terms_of_use: String,
    val supported_codes: List<List<String>>
)