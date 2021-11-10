package com.example.yahoofinance.domain.usecases

import androidx.lifecycle.LiveData
import com.example.yahoofinance.data.api.models.actives.TodaysActivesItem
import com.example.yahoofinance.data.repository.FinanceRepository

class GetTodaysActivesUseCase(private val repository: FinanceRepository) {
    fun getTodaysActives() : LiveData<List<TodaysActivesItem>> = repository.getTodaysActives()
}