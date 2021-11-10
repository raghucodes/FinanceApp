package com.example.yahoofinance.data.api.models.news

data class NewsItem(
    val image: String,
    val publishedDate: String,
    val site: String,
    val symbol: String,
    val text: String,
    val title: String,
    val url: String
)