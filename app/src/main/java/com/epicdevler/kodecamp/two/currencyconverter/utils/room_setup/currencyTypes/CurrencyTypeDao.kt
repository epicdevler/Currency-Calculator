package com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.currencyTypes

import androidx.room.*
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.ConversionRates
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.CurrencyTypesModel
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.TestCurrencyRates
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllTypes(currencyType: CurrencyTypesModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrencyRates(conversionRates: ConversionRates)

    @Query("SELECT COUNT(*) FROM CC_AVAILABLE_CURRENCY_TYPES")
    fun doesCurrenciesExists(): Int

    @Query("SELECT CONVERSION_RATE FROM CC_CONVERSION_RATE WHERE BASE_CURRENCY = :base_code AND TARGET_CODE = :toCurrency")
    fun getBaseCurrencyRate(base_code: String, toCurrency: String): Double

    @Update
    suspend
    fun updateCurrencyRates(conversionRates: ConversionRates)

    @Update
    suspend fun updateType(currencyType: CurrencyTypesModel)

    @Query("SELECT * FROM CC_AVAILABLE_CURRENCY_TYPES")
    fun getCurrencies(): Flow<List<CurrencyTypesModel>>

}