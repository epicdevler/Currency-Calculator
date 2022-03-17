package com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response

data class ConversionResponse(
    val isSuccess: Boolean = false,
    val error: String? = null,
    val conversionValue: ConversionValue? = ConversionValue("0.00", "000")
)

