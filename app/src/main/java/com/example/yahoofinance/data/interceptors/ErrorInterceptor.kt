package com.example.yahoofinance.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        when (response.code) {
            400 -> throw Exception() // Bad Request
            401 -> throw Exception() // Un-Authorized
            403 -> throw Exception() // Forbidden
            404 -> throw Exception() // Not Found
            500 -> throw Exception() // Internal Server Error
        }
        return response
    }
}