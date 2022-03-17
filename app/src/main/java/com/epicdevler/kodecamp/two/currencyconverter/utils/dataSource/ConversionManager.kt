package com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource

import android.util.Log
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.databaseAccess.DbController
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.ExchangeRateRepo
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response.ConversionValue
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response.ResourceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversionManager(
    private val dbController: DbController,
    private val exchangeRateRepo: ExchangeRateRepo
) {
    suspend fun convert(
        base_currency: String,
        toCurrency: String,
        amount: String
    ): ResourceResponse<String, ConversionValue> {
        return withContext(Dispatchers.IO) {
            try {
                val rates = dbController.getBaseCurrencyRate(base_currency, toCurrency)
                Log.e("ConvertTAG", "convert: $rates")
                if (rates != 0.0) {
                    val final = (amount.toDouble() * rates).toString()

                    prettifyResponse(final)
                } else {
                    when (val r = exchangeRateRepo.convert(
                        base_currency,
                        toCurrency,
                        amount
                    )) {
                        is ResourceResponse.Success -> {
                            dbController.saveCurrentBaseRate(r.data!!)
                            val final =
                                (amount.toDouble() * r.data.CONVERSION_RATE!!.toDouble()).toString()
                            prettifyResponse(final)
                        }
                        is ResourceResponse.Error -> {
                            ResourceResponse.Error(errorMsg = r.errorMsg)
                        }
                    }
                }
            } catch (e: Exception) {
                ResourceResponse.Error("$e")
            }

        }
    }

    private fun prettifyResponse(final: String): ResourceResponse<String, ConversionValue> {
        return when {
            final.contains('.') -> {
                val subString = final.split('.')
                var main = subString[0] + "."
                var remainder = subString[1]
                when {
                    remainder.length > 2 -> {
                        main += remainder.substring(0, 2)
                        remainder = remainder.substring(2)
                    }
                }
                ResourceResponse.Success(
                    data = ConversionValue(
                        value = main,
                        remainder = remainder
                    )
                )
            }
            else -> ResourceResponse.Success(data = ConversionValue(value = final))
        }
    }

}