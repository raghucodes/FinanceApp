package com.example.yahoofinance.ui.searchcompanies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yahoofinance.data.api.models.searchticker.SearchTickerItem
import com.example.yahoofinance.domain.usecases.SearchCompaniesUseCase

class SearchCompaniesViewModel(private val searchCompaniesUseCase: SearchCompaniesUseCase):ViewModel() {
    fun searchCompanies(searchString: String) : LiveData<List<SearchTickerItem>> = searchCompaniesUseCase.searchCompanies(searchString)
}