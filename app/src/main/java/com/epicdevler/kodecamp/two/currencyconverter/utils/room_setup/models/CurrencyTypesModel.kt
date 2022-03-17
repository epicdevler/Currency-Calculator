package com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CC_AVAILABLE_CURRENCY_TYPES")
data class CurrencyTypesModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CURRENCY_ID")
    val currencyId: Int = 0,
    @ColumnInfo(name = "CURRENCY_CODE")
    val currencyCode: String? = null,
    @ColumnInfo(name = "CURRENCY_NAME")
    val currencyName: String? = null,
    /*@ColumnInfo(name = "CURRENCY_RATE")
    val currencyRate: String*/
)

