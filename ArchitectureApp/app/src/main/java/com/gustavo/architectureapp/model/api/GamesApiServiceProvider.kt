package com.gustavo.architectureapp.model.api

import com.gustavo.architectureapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GamesApiServiceProvider {

    fun retrofitInstance() : GamesApiService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpInterceptor())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(GamesApiService::class.java)
    }

    private fun httpInterceptor() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

}