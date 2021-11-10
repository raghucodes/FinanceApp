package com.example.yahoofinance.data.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yahoofinance.data.api.interfaces.FinanceApi
import com.example.yahoofinance.data.api.models.actives.TodaysActivesItem
import com.example.yahoofinance.data.api.models.chart.ChartItem
import com.example.yahoofinance.data.api.models.companyprofile.CompanyProfileItem
import com.example.yahoofinance.data.api.models.majorindexes.MajorIndexesItem
import com.example.yahoofinance.data.api.models.news.News
import com.example.yahoofinance.data.api.models.news.NewsItem
import com.example.yahoofinance.data.api.models.searchticker.SearchTickerItem
import com.example.yahoofinance.ui.appContext
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FinanceRepository(private val financeApi: FinanceApi) {

    fun getTodaysActives() : LiveData<List<TodaysActivesItem>>{
        val actives = MutableLiveData<List<TodaysActivesItem>>()
        financeApi.getTodaysActives().enqueue(object : Callback<List<TodaysActivesItem>>{
            override fun onFailure(call: Call<List<TodaysActivesItem>>, t: Throwable) {
                //Toasty.error(this,"cannot connect to api", Toast.LENGTH_LONG).show()
                Log.e("repo error","couldn't connect to api")
                Log.e("precise error is ",t.toString())
            }

            override fun onResponse(call: Call<List<TodaysActivesItem>>, response: Response<List<TodaysActivesItem>>) {
             if(response.body() != null)  {
                 actives.value = response.body()
             }else{
                 Log.e("error","retrieved data is null")
             }
            }
        })
        return actives
    }

    fun searchCompanies(searchString: String) : LiveData<List<SearchTickerItem>>{
        val searchResults = MutableLiveData<List<SearchTickerItem>>()
        financeApi.searchCompanies(searchString).enqueue(object : Callback<List<SearchTickerItem>>{
            override fun onFailure(call: Call<List<SearchTickerItem>>, t: Throwable) {
                Log.e("repo error","couldn't connect to api")
                Log.e("precise error is ",t.toString())
            }
            override fun onResponse(
                call: Call<List<SearchTickerItem>>,
                response: Response<List<SearchTickerItem>>
            ) {
                if(response.body() != null) {
                    searchResults.value = response.body()
                }else{
                    Log.e("error","retrieved data is null")
                }
            }
        })
        return searchResults
    }

    fun getStockNews() : LiveData<List<NewsItem>>{
        val news = MutableLiveData<List<NewsItem>>()
        financeApi.getStockNews().enqueue(object : Callback<List<NewsItem>>{
            override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
                Log.e("repo error","couldn't connect to api")
                Log.e("precise error is ",t.toString())
            }

            override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
               if(response.body() != null){
                   news.value = response.body()
               }else{
                   Log.e("error","retrieved data is null")
               }
            }
        })
        return news
    }

    fun getMajorIndexes() : LiveData<List<MajorIndexesItem>>{
        val majorIndexes = MutableLiveData<List<MajorIndexesItem>>()
        financeApi.getMajorIndexes().enqueue(object  : Callback<List<MajorIndexesItem>>{
            override fun onFailure(call: Call<List<MajorIndexesItem>>, t: Throwable) {
                Log.e("repo error","couldn't connect to api")
                Log.e("precise error is ",t.toString())
            }

            override fun onResponse(
                call: Call<List<MajorIndexesItem>>,
                response: Response<List<MajorIndexesItem>>
            ) {
                if(response.body() != null){
                    majorIndexes.value = response.body()
                }else{
                    Log.e("error","retrieved data is null")
                }
            }

        })
        return majorIndexes
    }

    fun getHistoricalPrices(time : String,ticker : String) : LiveData<List<ChartItem>>{
        var prices = MutableLiveData<List<ChartItem>>()
        financeApi.getHistoricalPrices(time,ticker).enqueue(object : Callback<List<ChartItem>>{
            override fun onFailure(call: Call<List<ChartItem>>, t: Throwable) {
                Log.e("repo error","couldn't connect to api")
                Log.e("precise error is ",t.toString())
            }
            override fun onResponse(call: Call<List<ChartItem>>, response: Response<List<ChartItem>>) {
                if(response.body() != null){
                    prices.value = response.body()
                }else{
                    Log.e("error","retrieved data is null")
                }
            }
        })
        return prices
    }

    fun getCompanyProfile(ticker: String) : LiveData<List<CompanyProfileItem>>{
        var profile = MutableLiveData<List<CompanyProfileItem>>()
        financeApi.getCompanyProfile(ticker).enqueue(object : Callback<List<CompanyProfileItem>>{
            override fun onFailure(call: Call<List<CompanyProfileItem>>, t: Throwable) {
                Log.e("repo error","couldn't connect to api")
                Log.e("precise error is ",t.toString())
            }

            override fun onResponse(call: Call<List<CompanyProfileItem>>, response: Response<List<CompanyProfileItem>>) {
                if(response.body() != null){
                    profile.value = response.body()
                }else{
                    Log.e("error","retrieved data is null")
                }
            }
        })
        return profile
    }

}