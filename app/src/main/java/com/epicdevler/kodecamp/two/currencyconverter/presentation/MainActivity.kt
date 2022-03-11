package com.epicdevler.kodecamp.two.currencyconverter.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.epicdevler.kodecamp.two.currencyconverter.databinding.ActivityMainBinding
import com.epicdevler.kodecamp.two.currencyconverter.utils.snackBar.SnackError
import com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels.CurrencyConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyConverterViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(this)[CurrencyConverterViewModel::class.java].also { viewModel = it }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUiFun()

        handleViewModelObservers()
    }

    private fun initUiFun() {
        binding.mainActivityContent.convert.setOnClickListener {
            viewModel.test()
        }
    }

    private fun handleViewModelObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                SnackError(binding.root, it).show()
            }
        }
    }
}