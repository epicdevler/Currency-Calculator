package com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.api

import com.epicdevler.kodecamp.two.currencyconverter.utils.models.exchangeRateApi.codeResponse.CodeResponse
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.EnrichedDataModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ExchangeRateAPIService {
    @GET("latest/{base_code}")
    fun get(
        @Path("base_code")
        base_code: String
    ): Call<JsonObject>

    @GET("codes")
    fun getSupportedCodes(): Call<JsonObject>

    @GET
    fun convertTestAuto(
        @Url
        url: String
    ): Call<JsonObject>

    @GET
    fun getRate(
        @Url url: String
    ): Call<EnrichedDataModel>

}