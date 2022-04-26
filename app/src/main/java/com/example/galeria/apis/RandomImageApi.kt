package com.example.galeria.apis

import com.example.galeria.models.pageOfImagesModel.PageModel
import com.example.galeria.models.randomImageModel.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomImageApi {
    @GET("/photos/random")
    suspend fun getRandomImage(@Query("client_id") client_id: String, @Query("orientation") orientation: String): Response<Image>

    @GET("/search/photos/")
    suspend fun getListOfImages(@Query("client_id") client_id: String, @Query("page") page: String, @Query("query") query: String, @Query("order_by") order_by: String): Response<PageModel>
}