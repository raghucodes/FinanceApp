package com.example.yahoofinance.ui.companyprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yahoofinance.data.api.models.chart.ChartItem
import com.example.yahoofinance.data.api.models.companyprofile.CompanyProfileItem
import com.example.yahoofinance.domain.usecases.GetCompanyProfileUseCase
import com.example.yahoofinance.domain.usecases.GetHistoricalPricesUseCase

class CompanyProfileViewModel(private val getHistoricalPricesUseCase: GetHistoricalPricesUseCase , private val getCompanyProfileUseCase: GetCompanyProfileUseCase) : ViewModel() {
    fun getHistoricalPrices(time : String,ticker : String) : LiveData<List<ChartItem>> = getHistoricalPricesUseCase.getHistoricalPrices(time,ticker)
    fun getCompanyProfile(ticker : String) : LiveData<List<CompanyProfileItem>> = getCompanyProfileUseCase.getCompanyProfile(ticker)
}