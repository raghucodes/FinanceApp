package com.example.yahoofinance.data.api.models.chart

data class ChartItem(
    val close: Double,
    val date: String,
    val high: Double,
    val low: Double,
    val `open`: Double,
    val volume: Int
)