package com.example.yahoofinance.domain.usecases

import androidx.lifecycle.LiveData
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.data.repository.FinanceRepository

class GetStockNewsUseCase(private val repository: FinanceRepository) {
    fun getStockNews() : LiveData<List<NewsItem>> = repository.getStockNews()
}