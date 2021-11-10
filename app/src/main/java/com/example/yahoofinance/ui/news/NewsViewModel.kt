package com.example.yahoofinance.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yahoofinance.data.api.models.majorindexes.MajorIndexesItem
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.domain.usecases.GetMajorIndexesUseCase
import com.example.yahoofinance.domain.usecases.GetStockNewsUseCase

class NewsViewModel(private val getStockNewsUseCase: GetStockNewsUseCase,private val getMajorIndexesUseCase: GetMajorIndexesUseCase):ViewModel() {
    val stockNews : LiveData<List<NewsItem>> = getStockNewsUseCase.getStockNews()
    val majorIndexes : LiveData<List<MajorIndexesItem>> = getMajorIndexesUseCase.getMajorIndexes()
   // fun getStockNews() : LiveData<List<NewsItem>> = getStockNewsUseCase.getStockNews()
}