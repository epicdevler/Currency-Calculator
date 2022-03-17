package com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.ConversionManager
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.databaseAccess.DbController
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.ExchangeRateRepo
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response.ConversionResponse
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.response.ResourceResponse
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.ConversionRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val exchangeRateRepo: ExchangeRateRepo,
    private val dbController: DbController
) : ViewModel() {

    private val _uiConversionState = Channel<ConversionResponse>()
    val uiConversionState = _uiConversionState.receiveAsFlow()

    private val _uiState = Channel<UiState>()
    val uiState = _uiState.receiveAsFlow()

    /*
    Save currency to convert to and from into the
    @variable(currencyTo) and @variable(currencyFrom)
    */
    private var currencyTo: String = "AED"
    private var currencyFrom: String = "AED"

    fun convert(amount: String) {
        viewModelScope.launch {
            when (amount.isEmpty()) {
                true -> _uiConversionState.send(ConversionResponse(error = "Input an amount to convert"))
                else -> {
//                    exchangeRateConverterRepo.convert("", "", "")
                    _uiConversionState.send(
                        when (val con = ConversionManager(dbController, exchangeRateRepo).convert(
                            currencyFrom,
                            currencyTo,
                            amount
                        )) {
                            is ResourceResponse.Success -> {
                                ConversionResponse(
                                    isSuccess = true,
                                    conversionValue = con.data?.let {
                                        con.data
                                    })
                            }
                            is ResourceResponse.Error -> ConversionResponse(error = "Something went wrong {${con.errorMsg}}")
                        }
                    )
                }
            }
        }
    }

    fun getRates() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(ExchangeRateRepo.TAG, "test: Starting")
            val dbCodesCount = dbController.checkIfCodesAreAvailable()
            when {
                dbCodesCount <= 0 -> {
                    _uiState.send(UiState(isLoading = true, loadingMsg = "Fetching resources from servers"))
                    when (val codeResponse = exchangeRateRepo.getSupportedCodes()) {
                        is ResourceResponse.Success -> {
                            Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                            /*val rateResponse = exchangeRateRepo.getExchangeRate(
                        base_code = currencyFrom, currencyTo, dbController
                    )
                    when (rateResponse) {
                        is ResourceResponse.Error -> {
                            _uiState.send(UiState(error = rateResponse.errorMsg))
                        }
                        is ResourceResponse.Success -> {
                            *//*                            Log.e(
                                                                ExchangeRateRepo.TAG,
                                                                "viewModel: BaseCode ${rateResponse.data}"
                                                            )
                                                            Log.e(
                                                                ExchangeRateRepo.TAG,
                                                                "viewModel: BaseCode ${rateResponse.data?.get(1)?.BASE_CURRENCY}"
                                                            )*//*
                                Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                                dbController.saveSupportedCurrencyTypes(
                                    rateResponse.data,
                                    codeResponse.data
                                )
                                Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                                _uiState.send(UiState(isSuccess = true))
                            }
                        }*/
                            Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                            dbController.saveSupportedCurrencyTypes(
                                codes = codeResponse.data
                            )
                            Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                            _uiState.send(UiState(isSuccess = true, new = true))
                        }
                        is ResourceResponse.Error -> {
                            _uiState.send(UiState(error = codeResponse.errorMsg))
                        }
                    }
                }
                else -> {
                    _uiState.send(UiState(isSuccess = true))
                }
            }
        }
    }

    private fun getCurrentBaseCodeRates() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.send(UiState(isLoading = true, loadingMsg = "Getting data"))
            val rateResponse: ResourceResponse<String, ConversionRates> =
                exchangeRateRepo.convert(
                    currencyFrom,
                    currencyTo,
                    "0"
                )
            when (rateResponse) {
                is ResourceResponse.Error -> {
                    _uiState.send(UiState(error = rateResponse.errorMsg))
                }
                is ResourceResponse.Success -> {
                    Log.e(
                        ExchangeRateRepo.TAG,
                        "test: BaseCode ${rateResponse.data!!.BASE_CURRENCY}"
                    )
                    Log.e(
                        ExchangeRateRepo.TAG,
                        "test: BaseCode ${rateResponse.data}"
                    )
                    dbController.saveCurrentBaseRate(rateResponse.data)
                    _uiState.send(UiState(isSuccess = true))
                }
            }
            /*when (val codeResponse = exchangeRateRepo.getActiveBaseRate(
                dbController,
                base_code = currencyFrom,
                target_code = currencyTo
            )) {
                is ResourceResponse.Success -> {
                    Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                    dbController.saveCurrentBaseRate(
                        codeResponse.data
                    )
                    Log.e(ExchangeRateRepo.TAG, "test: Data $codeResponse.data")
                    _uiConversionState.send(ConversionResponse(isSuccess = true))
                }
                is ResourceResponse.Error -> {
                    _uiConversionState.send(ConversionResponse(error = codeResponse.errorMsg))
                }
            }*/
        }
    }

    private val _currencyToConvertTo: MutableLiveData<String> = MutableLiveData()
    val currencyToConvertTo = _currencyToConvertTo

    val liveCurrencyTypes = dbController.getAvailableCurrencies()

    fun updateCurrencyToConvertTo(convertTo: String) {
        currencyTo = convertTo
        with(_currencyToConvertTo) { value = convertTo }
        Log.e("TAG", "updateCurrencyToConvertTo: $convertTo & $currencyFrom")
    }

    private val _currencyToConvertFrom: MutableLiveData<String> = MutableLiveData()
    val currencyToConvertFrom = _currencyToConvertFrom
    fun updateCurrencyToConvertFrom(convertFrom: String) {
        currencyFrom = convertFrom
        getCurrentBaseCodeRates()
        with(_currencyToConvertFrom) { value = convertFrom }
        Log.e("TAG", "updateCurrencyToConvertFrom: $convertFrom & $currencyTo")
    }

}