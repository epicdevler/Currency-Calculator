package com.epicdevler.kodecamp.two.currencyconverter.utils.di

import android.content.Context
import com.epicdevler.kodecamp.two.currencyconverter.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DiSingletons {

    @Provides
    @Named("fixerAccessKey")
    fun getAccessKey(@ApplicationContext context: Context): String =
        context.getString(R.string.FIXER_ACCESS_KEY)

    @Provides
    @Named("baseURL")
    fun retrofitBaseUrl(): String = "http://data.fixer.io/api/latest"

    @Provides
    fun retrofit(
        @Named("baseURL")
        baseURL: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}