package com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.converstions.ConversionsDao
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.currencyTypes.CurrencyTypeDao
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.ConversionRates
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.CurrencyTypesModel
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.LastSelectedCurrencyTypeModel
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.TestCurrencyRates

@Database(
    entities = [CurrencyTypesModel::class, ConversionRates::class, TestCurrencyRates::class],
    version = 1,
    exportSchema = false
)

abstract class CCDatabase : RoomDatabase() {

    abstract fun conversionDao(): ConversionsDao
    abstract fun currencyTypeDao(): CurrencyTypeDao

    companion object {
        private var INSTANCE: CCDatabase? = null
        fun db(context: Context): CCDatabase {
            synchronized(this) {
                val tempInstance = INSTANCE
                if (tempInstance != null) return tempInstance
                val instance =
                    Room.databaseBuilder(context, CCDatabase::class.java, "CC_ACCESS_DB").build()
                INSTANCE = instance
                return instance

            }
        }
    }

}