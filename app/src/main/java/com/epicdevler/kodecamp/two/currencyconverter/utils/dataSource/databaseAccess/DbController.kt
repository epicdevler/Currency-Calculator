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
        rates: ArrayList<TestCurrencyRates>? = null,
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
            if (rates != null) {
                for (rate in rates){
                    currencyTypeDao.saveCurrencyRates(conversionRates = rate)
                }
            }
        }
    }

    suspend fun checkIfCodesAreAvailable(): Int = currencyTypeDao.doesCurrenciesExists()
    suspend fun checkIfRatesAreAvailable(base_code: String, currencyTo: String): Int = currencyTypeDao.doesCurrenciesRatesExists(base_code, currencyTo)
    suspend fun checkIfBaseRatesIsAvailable(base_code: String, target_code: String): Int =
        currencyTypeDao.doesBaseCurrenciesRateExists(base_code, target_code)


    fun getAvailableCurrencies(): Flow<List<CurrencyTypesModel>> =
        currencyTypeDao.getCurrencies()

    suspend fun saveCurrentBaseRate(enrichedDataModel: ConversionRates?) {
        withContext(Dispatchers.IO) {
            val rate = ConversionRates()
            if (enrichedDataModel != null) {
                currencyTypeDao.saveCurrencyRates(
                    conversionRates = ConversionRates(
                        BASE_CURRENCY = enrichedDataModel.BASE_CURRENCY,
                        TARGET_CODE = enrichedDataModel.TARGET_CODE,
                        CONVERSION_RATE = enrichedDataModel.CONVERSION_RATE,
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