package com.epicdevler.kodecamp.two.currencyconverter.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.epicdevler.kodecamp.two.currencyconverter.databinding.ActivityMainBinding
import com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels.CurrencyConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyConverterViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(this)[CurrencyConverterViewModel::class.java].also { viewModel = it }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            delay(3000L)

        }


    }
}