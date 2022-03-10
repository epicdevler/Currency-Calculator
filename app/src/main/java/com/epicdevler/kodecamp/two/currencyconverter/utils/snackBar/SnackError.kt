package com.epicdevler.kodecamp.two.currencyconverter.utils.snackBar

import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SnackError constructor(
    private val parentView: View,
    private val msg: String,
    private val duration: SnackDuration = SnackDuration.Short
) {

    fun show() {
        CoroutineScope(Dispatchers.IO).launch {
            Snackbar.make(parentView, msg, duration.length).show()
        }
    }

}