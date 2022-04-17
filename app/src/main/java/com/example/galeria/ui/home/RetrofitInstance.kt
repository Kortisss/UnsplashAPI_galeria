package com.example.galeria.ui.home

import com.example.galeria.apis.RandomImageApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val API: RandomImageApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomImageApi::class.java)
    }
}