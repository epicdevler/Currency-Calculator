package com.epicdevler.kodecamp.two.currencyconverter.presentation

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.epicdevler.kodecamp.two.currencyconverter.databinding.ActivityMainBinding
import com.epicdevler.kodecamp.two.currencyconverter.utils.adapters.CustomSpinnerAdapter
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.CurrencyTypesModel
import com.epicdevler.kodecamp.two.currencyconverter.utils.snackBar.SnackDuration
import com.epicdevler.kodecamp.two.currencyconverter.utils.snackBar.SnackError
import com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels.CurrencyConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyConverterViewModel by lazy { ViewModelProvider(this)[CurrencyConverterViewModel::class.java] }
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val list: ArrayList<CurrencyTypesModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleViewModelObservers()
        initUiFun()
    }

    private fun initUiFun() {

        binding.mainActivityContent.convert.setOnClickListener {
            getDataFromInput()
        }
        handleSpinnerSelections()
        activateSpinner()

    }

    private fun getDataFromInput() {
        binding.mainActivityContent.inputLayout.currencyAmountInput.text.apply {
            viewModel.convert(amount = this?.trim().toString())
        }
    }

    private fun handleSpinnerSelections() {

        lifecycleScope.launchWhenCreated {
            viewModel.liveCurrencyTypes.collect {
                initSpinnerAdapters(it)
                list.addAll(it)
            }
        }
    }

    private fun initSpinnerAdapters(it: List<CurrencyTypesModel>) {
        val customAdapter = CustomSpinnerAdapter(this, it)
        binding.mainActivityContent.convertTypeSelectors.apply {
            convertFrom.adapter = customAdapter
            convertTo.adapter = customAdapter
        }
    }

    private fun handleViewModelObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiConversionState.collect {
                when {
                    it.isSuccess -> {
                        /*SnackError(
                            binding.root,
                            "${it.conversionValue?.value}${it.conversionValue?.remainder}"
                        ).show()*/
                        binding.mainActivityContent.inputLayout.apply {
                            convertValue.text =
                                it.conversionValue?.value
                            convertRemainder.text =
                                it.conversionValue?.remainder
                        }
                    }
                    it.error != null -> {
                        SnackError(
                            binding.root,
                            "${it.error}",
                            duration = SnackDuration.Long
                        ).show()
                    }
                }
            }
        }

        viewModel.currencyToConvertFrom.observe(this) {
            binding.mainActivityContent.inputLayout.currencyFrom.text = it
            /*getDataFromInput()*/
        }
        viewModel.currencyToConvertTo.observe(this) {
            binding.mainActivityContent.inputLayout.currencyTo.text = it
            /*getDataFromInput()*/
        }

    }

    private fun activateSpinner() {
        lifecycleScope.launch {
            binding.mainActivityContent.convertTypeSelectors.apply {
                convertFrom.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        @SuppressLint("RestrictedApi")
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            Log.e("mainTAG", "onItemSelected: $p2")
                            viewModel.updateCurrencyToConvertFrom(list[p2].currencyCode!!)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }

                convertTo.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        @SuppressLint("RestrictedApi")
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            Log.e("mainTAG", "onItemSelected: $p2")
                            viewModel.updateCurrencyToConvertTo(list[p2].currencyCode!!)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
            }
        }
    }
}