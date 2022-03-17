package com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models

import androidx.annotation.NonNull
import androidx.room.Entity

@Entity(tableName = "CC_TEST_CURRENCY_RATES", primaryKeys = ["CURRENCY", "BASE_CURRENCY"])
data class TestCurrencyRates(
    @NonNull
    val CURRENCY: String,
    @NonNull
    val BASE_CURRENCY: String,
    val CURRENCY_RATE: String? = null
)