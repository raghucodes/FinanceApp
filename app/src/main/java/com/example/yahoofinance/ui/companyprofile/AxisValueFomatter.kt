package com.example.yahoofinance.ui.companyprofile

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class AxisValueFomatter: ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        try {
            val date = Date(value.toLong())
            val format = SimpleDateFormat("HH:mm:ss")
            Log.e("formatted string",format.format(date))
            return format.format(date)
//            val sdf = SimpleDateFormat("MM/dd/yyyy")
//            val netDate = Date(value.toLong() * 1000)
//            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}