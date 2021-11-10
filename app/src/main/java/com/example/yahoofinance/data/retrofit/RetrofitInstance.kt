package com.example.yahoofinance.data.retrofit

import com.example.yahoofinance.data.api.interfaces.FinanceApi
import com.example.yahoofinance.data.interceptors.ErrorInterceptor
import com.example.yahoofinance.ui.appContext
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit

const val API_KEY = "73c0865d4801a26a68a658a780057b14"
const val BASE_URL = "https://financialmodelingprep.com"
class RetrofitInstance {

    companion object {
        var httpCacheDirectory: File = File(appContext.cacheDir, "httpCache")
        var cache: Cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

        private val interceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("apikey", API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        private val cacheInterceptor = Interceptor { chain ->
            try {
                return@Interceptor chain.proceed(chain.request());
            } catch (e : Exception) {
                val offlineRequest: Request = chain.request().newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached," + "max-stale=" + 60 * 60 * 24
                    )
                    .build()
                return@Interceptor chain.proceed(offlineRequest)
            }
        }

        fun httpClient(): OkHttpClient {
            return OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                    .addInterceptor(cacheInterceptor)
                    .addInterceptor(ErrorInterceptor())
                    .cache(cache)
                    .addNetworkInterceptor(StethoInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
            }.build()
        }

        fun getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        fun provideFinanceApi(retrofit: Retrofit) = retrofit.create(FinanceApi::class.java)
    }
}