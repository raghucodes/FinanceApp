package com.example.yahoofinance.domain.usecases

import androidx.lifecycle.LiveData
import com.example.yahoofinance.data.api.models.companyprofile.CompanyProfileItem
import com.example.yahoofinance.data.repository.FinanceRepository

class GetCompanyProfileUseCase(private val repository: FinanceRepository) {
    fun getCompanyProfile(ticker : String) : LiveData<List<CompanyProfileItem>> = repository.getCompanyProfile(ticker)
}