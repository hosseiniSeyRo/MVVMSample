package com.rhosseini.mvvmsample.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    val WEB_SERVICE_BASE_URL = "http://www.moviesapi.ir/api/"
    private var retrofit: Retrofit? = null

    private val retrofitClient by lazy {
        Retrofit.Builder()
            .baseUrl(WEB_SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofitClient.create(serviceClass)
    }

}