package com.example.yahoofinance.data.api.models.actives

data class TodaysActivesItem(
    val changes: Double,
    val changesPercentage: String,
    val companyName: String,
    val price: String,
    val ticker: String
)