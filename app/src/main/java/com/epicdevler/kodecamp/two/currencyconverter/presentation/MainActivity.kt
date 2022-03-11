package com.epicdevler.kodecamp.two.currencyconverter.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.adapters.AdapterViewBindingAdapter
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

        val currencies = listOf(
            "EUR",
            "PLN",
            "NGN",
            "USD",
            "BTC"
        )


        binding.mainActivityContent.convertTypeSelectors.convertFrom.onItemSelectedListener =
            object : AdapterViewBindingAdapter.OnItemSelected,
                AdapterView.OnItemSelectedListener {
                @SuppressLint("RestrictedApi")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateCurrencyToConvertFrom(currencies[position])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        binding.mainActivityContent.convertTypeSelectors.convertTo.onItemSelectedListener =
            object : AdapterViewBindingAdapter.OnItemSelected,
                AdapterView.OnItemSelectedListener {
                @SuppressLint("RestrictedApi")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateCurrencyToConvertTo(currencies[position])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

    }

    private fun handleViewModelObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                SnackError(binding.root, it).show()
            }
        }


        viewModel.currencyToConvertFrom.observe(this) {
            SnackError(binding.root, it).show()
        }
        viewModel.currencyToConvertTo.observe(this) {
            SnackError(binding.root, it).show()
        }
    }
}