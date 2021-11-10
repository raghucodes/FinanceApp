package com.example.yahoofinance

import android.app.Application
import com.example.yahoofinance.data.repository.FinanceRepository
import com.example.yahoofinance.data.retrofit.RetrofitInstance.Companion.getRetrofitInstance
import com.example.yahoofinance.data.retrofit.RetrofitInstance.Companion.httpClient
import com.example.yahoofinance.data.retrofit.RetrofitInstance.Companion.provideFinanceApi
import com.example.yahoofinance.domain.usecases.*
import com.example.yahoofinance.ui.actives.ActivesViewModel
import com.example.yahoofinance.ui.companyprofile.CompanyProfileViewModel
import com.example.yahoofinance.ui.networkstate.NetworkStateHolder.registerConnectivityMonitor
import com.example.yahoofinance.ui.news.NewsViewModel
import com.example.yahoofinance.ui.searchcompanies.SearchCompaniesViewModel
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        registerConnectivityMonitor()
        Stetho.initializeWithDefaults(this)
        startKoin{
        androidContext(this@App)
        modules(listOf(appModule))
        }
    }
}

val appModule = module {

    factory { httpClient() }

    factory { getRetrofitInstance(get()) }

    single { provideFinanceApi(get()) }

    factory { FinanceRepository(get()) }

    factory { GetTodaysActivesUseCase(get()) }

    factory { SearchCompaniesUseCase(get()) }

    factory { GetStockNewsUseCase(get()) }

    factory { GetHistoricalPricesUseCase(get()) }

    factory { GetCompanyProfileUseCase(get()) }

    factory { GetMajorIndexesUseCase(get()) }

    viewModel { ActivesViewModel(get()) }

    viewModel { SearchCompaniesViewModel(get()) }

    viewModel { NewsViewModel(get(),get()) }

    viewModel { CompanyProfileViewModel(get(),get()) }
}