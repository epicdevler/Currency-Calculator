package com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos

import android.util.Log
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.api.ExchangeRateAPIService
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response.ResourceResponse
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.ConversionRates
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExchangeRateRepo(
    val exchangeRateAPIService: ExchangeRateAPIService,
    private val baseUrl: String
) {

    companion object {
        const val TAG: String = "EXCHANGE_RATE_REPO"
    }

    suspend fun getSupportedCodes(): ResourceResponse<String, JsonArray> {
        return withContext(Dispatchers.IO) {
            try {
                val response = exchangeRateAPIService.getSupportedCodes().awaitResponse()
                Log.e(TAG, "test: Data test $response")
                Log.e(TAG, "test: Data test ${response.raw()}")
                if (response.code() == 403) {
                    ResourceResponse.Error("Server not responding")
                } else {
                    Log.e(TAG, "test: Data test ${response.body()}")
                    Log.e(TAG, "test: Data test ${response.body()?.get("result")?.asString}")
                    Log.e(
                        TAG,
                        "test: Data test ${response.body()?.get("supported_codes")?.asJsonArray}"
                    )
                    val codes = response.body()?.get("supported_codes")!!.asJsonArray
                    when (response.body()?.get("result")?.asString) {
                        "success" -> ResourceResponse.Success(codes)
                        "error" -> {
                            Log.e(
                                TAG,
                                "test: Data test ${response.body()}"
                            )
                            ResourceResponse.Error(errorMsg = "${response.errorBody()}")
                        }
                        else -> ResourceResponse.Error(errorMsg = "Something went wrong")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "test: Data test $e")
                ResourceResponse.Error(
                    errorMsg =
                    when (e) {
                        is UnknownHostException -> {
                            "Network Error Please check your internet connection"
                        }
                        is SocketTimeoutException -> {
                            "Network timeout, check you internet connection and try again"
                        }
                        else -> "Something went wrong please check you internet connection and try again"
                    }
                )
            }
        }
    }

    suspend fun convert(
        base_currency: String,
        toCurrency: String,
        amount: String
    ): ResourceResponse<String, ConversionRates> {
        val url = "${baseUrl}pair/$base_currency/$toCurrency/$amount"
        val c = exchangeRateAPIService.convertTestAuto(url).awaitResponse()
        val r = c.body()?.asJsonObject
        Log.e(TAG, "convert Url: $c")
        Log.e(TAG, "convert Url: $url")
        Log.e(TAG, "convert: $r")
        return if (r?.get("result")?.asString == "success") {
            val rate = ConversionRates(
                BASE_CURRENCY = r.get("base_code")?.asString,
                TARGET_CODE = r.get("target_code")?.asString,
                CONVERSION_RATE = r.get("conversion_rate")?.asDouble,
            )
            Log.e(TAG, "convert: $rate")
            ResourceResponse.Success(rate)
        } else {
            ResourceResponse.Error("Err")
        }
    }

    suspend fun getActive(
        base_currency: String,
        toCurrency: String,
        amount: String
    ): ResourceResponse<String, ConversionRates> {
        val url = "${baseUrl}pair/$base_currency/$toCurrency/$amount"
        val c = exchangeRateAPIService.convertTestAuto(url).awaitResponse()
        val r = c.body()?.asJsonObject
        Log.e(TAG, "convert Url: $c")
        Log.e(TAG, "convert Url: $url")
        Log.e(TAG, "convert: $r")
        return if (r?.get("result")?.asString == "success") {
            val rate = ConversionRates(
                BASE_CURRENCY = r.get("base_code")?.asString,
                TARGET_CODE = r.get("target_code")?.asString,
                CONVERSION_RATE = r.get("conversion_rate")?.asDouble,
            )
            Log.e(TAG, "convert: $rate")
            ResourceResponse.Success(rate)
        } else {
            ResourceResponse.Error("Err")
        }
    }


}