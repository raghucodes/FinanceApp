package com.example.yahoofinance.data.api.models.searchticker

data class SearchTickerItem(
    val currency: String,
    val exchangeShortName: String,
    val name: String,
    val stockExchange: String,
    val symbol: String
)