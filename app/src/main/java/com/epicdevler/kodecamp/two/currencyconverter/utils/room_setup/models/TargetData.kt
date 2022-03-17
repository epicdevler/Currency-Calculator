package com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models

data class TargetData(
    val currency_name: String,
    val currency_name_short: String,
    val display_symbol: String,
    val flag_url: String,
    val locale: String,
    val two_letter_code: String
)