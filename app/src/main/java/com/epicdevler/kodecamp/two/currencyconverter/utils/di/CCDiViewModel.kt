package com.epicdevler.kodecamp.two.currencyconverter.utils.di

import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.databaseAccess.DbController
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.ExchangeRateRepo
import com.epicdevler.kodecamp.two.currencyconverter.utils.dataSource.repos.api.ExchangeRateAPIService
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.CCDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object CCDiViewModel {

    @Provides
    @ViewModelScoped
    fun getAPIRepo(
        exchangeRateAPIService: ExchangeRateAPIService,
        @Named("baseURL")
        baseUrl : String
    ): ExchangeRateRepo =
        ExchangeRateRepo(exchangeRateAPIService = exchangeRateAPIService, baseUrl)


    @Provides
    @ViewModelScoped
    fun getCurrencyTypeDao(
        ccDatabase: CCDatabase
    ): DbController = DbController(currencyTypeDao = ccDatabase.currencyTypeDao())


}