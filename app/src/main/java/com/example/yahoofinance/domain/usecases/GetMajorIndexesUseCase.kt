package com.example.yahoofinance.domain.usecases

import androidx.lifecycle.LiveData
import com.example.yahoofinance.data.api.models.majorindexes.MajorIndexesItem
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.data.repository.FinanceRepository

class GetMajorIndexesUseCase(private val repository: FinanceRepository) {
    fun getMajorIndexes() : LiveData<List<MajorIndexesItem>> = repository.getMajorIndexes()
}