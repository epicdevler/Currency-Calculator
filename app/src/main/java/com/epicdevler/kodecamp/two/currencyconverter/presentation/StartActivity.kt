package com.epicdevler.kodecamp.two.currencyconverter.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.epicdevler.kodecamp.two.currencyconverter.R
import com.epicdevler.kodecamp.two.currencyconverter.databinding.StartActivityLayoutBinding
import com.epicdevler.kodecamp.two.currencyconverter.utils.viewModels.CurrencyConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private val binding: StartActivityLayoutBinding by lazy {
        StartActivityLayoutBinding.inflate(
            layoutInflater
        )
    }

    val btnState = MutableStateFlow("")
    private val viewModel: CurrencyConverterViewModel by lazy {
        ViewModelProvider(this)[CurrencyConverterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRates()
        setContentView(binding.root)

        lifecycleScope.launch {
            btnState.collect {
                when {
                    it.isNotEmpty() -> {
                        binding.done.apply {
                            text = "Retry"
                            visibility = VISIBLE
                            btnState.emit("")
                            setOnClickListener {
                                binding.eventAnim.visibility = GONE
                                binding.label.visibility = GONE
                                viewModel.getRates()
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when {
                    it.isLoading -> {
                        binding.loader.visibility = VISIBLE
                        binding.done.visibility = GONE
                        if (it.loadingMsg != null) binding.label.visibility = VISIBLE
                    }
                    else -> {
                        when {
                            it.isSuccess -> {
                                if (it.new) {
                                    initStartupSuccessUI()
                                } else {
                                    next()
                                }
                            }
                            it.error != null -> {
                                initErrorUI(it.error)
                                btnState.emit("retry")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initErrorUI(errorMsg: String) {
        binding.loader.visibility = GONE
        binding.eventAnim.setAnimation(R.raw.error)
        binding.eventAnim.visibility = VISIBLE
        binding.label.text = errorMsg
    }

    private fun initStartupSuccessUI() {
        lifecycleScope.launch {
            binding.loader.visibility = GONE
            binding.eventAnim.setAnimation(R.raw.success)
            binding.eventAnim.visibility = VISIBLE
            binding.label.text = getString(R.string.startupSuccessMsg)
            delay(500)
            binding.done.apply {
                visibility = VISIBLE
                setOnClickListener { next() }
            }
        }
    }

    private fun next() {
        viewModel.liveCurrencyTypes
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}

fun Activity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.showLongToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}