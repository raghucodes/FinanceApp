package com.example.yahoofinance.data.api.interfaces

import com.example.yahoofinance.data.api.models.majorindexes.MajorIndexes
import com.example.yahoofinance.data.api.models.actives.TodaysActivesItem
import com.example.yahoofinance.data.api.models.chart.ChartItem
import com.example.yahoofinance.data.api.models.companyprofile.CompanyProfileItem
import com.example.yahoofinance.data.api.models.majorindexes.MajorIndexesItem
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.data.api.models.searchticker.SearchTicker
import com.example.yahoofinance.data.api.models.searchticker.SearchTickerItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FinanceApi{

    @GET("/api/v3/quotes/index")
    fun getMajorIndexes() : Call<List<MajorIndexesItem>>

    @GET("/api/v3/actives")
    fun getTodaysActives() : Call<List<TodaysActivesItem>>

    @GET("/api/v3/search")
    fun searchCompanies(@Query("query") query : String) : Call<List<SearchTickerItem>>

    @GET("/api/v3/stock_news")
    fun getStockNews() : Call<List<NewsItem>>

    @GET("/api/v3/historical-chart/{time}/{ticker}")
    fun getHistoricalPrices(@Path("time")  time : String, @Path("ticker") ticker : String) : Call<List<ChartItem>>

    @GET("/api/v3/profile/{ticker}")
    fun getCompanyProfile(@Path("ticker")ticker : String) : Call<List<CompanyProfileItem>>

}