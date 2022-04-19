package com.example.galeria.apis

import com.example.galeria.models.randomImageModel.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomImageApi {
    @GET("/photos/random")
    suspend fun getRandomImage(@Query("client_id") key: String): Response<Image>

}