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
    suspend fun saveCurrencyRates(conversionRates: TestCurrencyRates)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrencyRates(conversionRates: ConversionRates)

    @Query("SELECT COUNT(*) FROM CC_AVAILABLE_CURRENCY_TYPES")
    fun doesCurrenciesExists(): Int

    @Query("SELECT COUNT(*) FROM CC_TEST_CURRENCY_RATES WHERE BASE_CURRENCY = :base_code AND CURRENCY = :currencyTo")
    fun doesCurrenciesRatesExists(base_code: String, currencyTo: String): Int

    @Query("SELECT COUNT(*) FROM CC_CONVERSION_RATE WHERE BASE_CURRENCY = :base_code AND TARGET_CODE = :target_code")
    fun doesBaseCurrenciesRateExists(base_code: String, target_code: String): Int

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