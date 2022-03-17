package com.epicdevler.kodecamp.two.currencyconverter.utils.di

import android.content.Context
import com.epicdevler.kodecamp.two.currencyconverter.R
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.api.ExchangeRateAPIService
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.CCDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiSingletons {

    @Singleton
    @Provides
    @Named("baseURL")
    fun retrofitBaseUrl(
        @ApplicationContext context: Context
    ): String = "https://v6.exchangerate-api.com/v6/${context.getString(R.string.API_ACCESS_KEY)}/"

    @Singleton
    @Provides
    fun retrofit(
        @Named("baseURL")
        baseURL: String
    ): ExchangeRateAPIService =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ExchangeRateAPIService::class.java)


    @Provides
    fun getDatabase(@ApplicationContext context: Context): CCDatabase =
        CCDatabase.db(context)


}