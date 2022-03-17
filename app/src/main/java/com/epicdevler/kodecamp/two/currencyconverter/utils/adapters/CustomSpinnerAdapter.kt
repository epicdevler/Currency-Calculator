package com.epicdevler.kodecamp.two.currencyconverter.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.epicdevler.kodecamp.two.currencyconverter.R
import com.epicdevler.kodecamp.two.currencyconverter.utils.room_setup.models.CurrencyTypesModel
import com.google.android.material.textview.MaterialTextView

class CustomSpinnerAdapter(context: Context, private val currencyCodes: List<CurrencyTypesModel>) :
    ArrayAdapter<CurrencyTypesModel>(context, 0, currencyCodes) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(context).inflate(R.layout.currency_spinner_layout, parent, false)
        val code = getItem(position)
        view.findViewById<MaterialTextView>(R.id.codeText).text = code!!.currencyCode

        return view

    }

}