package com.example.yahoofinance.ui.actives

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yahoofinance.data.api.models.actives.TodaysActivesItem
import com.example.yahoofinance.domain.usecases.GetTodaysActivesUseCase

class ActivesViewModel(private val getTodaysActivesUseCase: GetTodaysActivesUseCase):ViewModel() {
    val activesList : LiveData<List<TodaysActivesItem>> = getTodaysActivesUseCase.getTodaysActives()
  //  fun getTodaysActives() : LiveData<List<TodaysActivesItem>> = getTodaysActivesUseCase.getTodaysActives()
}