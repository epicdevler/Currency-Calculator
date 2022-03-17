package com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
foreignKeys = [
ForeignKey(
entity = CurrencyTypesModel::class,
onDelete = ForeignKey.CASCADE,
parentColumns = arrayOf("CURRENCY_ID"),
childColumns = arrayOf("LAST_CURRENCY_FROM_ID")
),
ForeignKey(
entity = CurrencyTypesModel::class,
onDelete = ForeignKey.CASCADE,
parentColumns = arrayOf("CURRENCY_ID"),
childColumns = arrayOf("LAST_CURRENCY_TO_ID")
)
]*/
@Entity(tableName = "CC_ACTIVE_CURRENCY_TYPE")
data class LastSelectedCurrencyTypeModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CURRENCY_ID")
    val currencyId: Int = 0,
    @ColumnInfo(name = "LAST_CURRENCY_FROM_CODE")
    val from: String,
    @ColumnInfo(name = "LAST_CURRENCY_TO_CODE")
    val to: String
)