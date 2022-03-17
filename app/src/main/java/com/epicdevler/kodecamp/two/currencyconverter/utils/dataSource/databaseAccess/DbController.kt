package com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.databaseAccess

import android.util.Log
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.ExchangeRateRepo
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.currencyTypes.CurrencyTypeDao
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.ConversionRates
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.CurrencyTypesModel
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.EnrichedDataModel
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.TestCurrencyRates
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DbController(val currencyTypeDao: CurrencyTypeDao) {

    suspend fun saveSupportedCurrencyTypes(
        codes: JsonArray? = null
    ) {
        withContext(Dispatchers.IO) {
            if (codes?.get(0) != null) {
                for (code in codes) {
                    currencyTypeDao.saveAllTypes(
                        CurrencyTypesModel(
                            currencyCode = code.asJsonArray[0].asString,
                            currencyName = code.asJsonArray[1].asString,
                        )
                    )
                }
            }
        }
    }

    suspend fun checkIfCodesAreAvailable(): Int = currencyTypeDao.doesCurrenciesExists()


    fun getAvailableCurrencies(): Flow<List<CurrencyTypesModel>> =
        currencyTypeDao.getCurrencies()

    suspend fun saveCurrentBaseRate(conversionRates: ConversionRates?) {
        withContext(Dispatchers.IO) {
            if (conversionRates != null) {
                currencyTypeDao.saveCurrencyRates(
                    conversionRates = ConversionRates(
                        BASE_CURRENCY = conversionRates.BASE_CURRENCY,
                        TARGET_CODE = conversionRates.TARGET_CODE,
                        CONVERSION_RATE = conversionRates.CONVERSION_RATE,
                    )
                )
            }
        }
    }

    suspend fun getBaseCurrencyRate(base_code: String, toCurrency: String): Double =
        withContext(Dispatchers.IO) {
            currencyTypeDao.getBaseCurrencyRate(base_code, toCurrency)
        }

}