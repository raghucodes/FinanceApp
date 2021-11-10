package com.example.yahoofinance.domain.usecases

import androidx.lifecycle.LiveData
import com.example.yahoofinance.data.api.models.searchticker.SearchTickerItem
import com.example.yahoofinance.data.repository.FinanceRepository

class SearchCompaniesUseCase(private val repository: FinanceRepository) {
    fun searchCompanies(searchString: String) : LiveData<List<SearchTickerItem>> = repository.searchCompanies(searchString)
}