package com.decagon.android.sq007.services

import com.decagon.android.sq007.interfaces.HttpRequests
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    // retrofit builder
    private fun getUploadApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    var retroAPIservice: HttpRequests = getUploadApi().create(HttpRequests::class.java)
}

