package com.example.yahoofinance.data.api.models.majorindexes

data class MajorIndexesItem(
    val avgVolume: Double,
    val change: Double,
    val changesPercentage: Double,
    val dayHigh: Double,
    val dayLow: Double,
    val earningsAnnouncement: String,
    val eps: String,
    val exchange: String,
    val marketCap: String,
    val name: String,
    val open: Double,
    val pe: Any,
    val previousClose: Double,
    val price: Double,
    val priceAvg200: Double,
    val priceAvg50: Double,
    val sharesOutstanding: String,
    val symbol: String,
    val timestamp: String,
    val volume: Double,
    val yearHigh: Double,
    val yearLow: Double
)