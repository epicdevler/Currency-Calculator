package com.epicdevler.kodecamp.two.currencyconverter.utils.snackBar

import com.google.android.material.snackbar.Snackbar

sealed class SnackDuration(val length: Int) {
    object Long : SnackDuration(Snackbar.LENGTH_LONG)
    object Short : SnackDuration(Snackbar.LENGTH_SHORT)
    object Indefinite : SnackDuration(Snackbar.LENGTH_INDEFINITE)
}
