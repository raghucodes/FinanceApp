package com.example.yahoofinance.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yahoofinance.data.api.models.chart.ChartItem
import com.example.yahoofinance.data.repository.FinanceRepository
import com.example.yahoofinance.domain.entities.ChartEntity
import com.github.mikephil.charting.data.Entry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GetHistoricalPricesUseCase(private val repository: FinanceRepository) {
    fun getHistoricalPrices(time : String,ticker : String) : LiveData<List<ChartItem>> = repository.getHistoricalPrices(time,ticker)
}
