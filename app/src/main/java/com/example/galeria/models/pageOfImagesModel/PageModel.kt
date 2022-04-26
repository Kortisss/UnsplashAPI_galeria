package com.example.galeria.models.pageOfImagesModel

data class PageModel(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)