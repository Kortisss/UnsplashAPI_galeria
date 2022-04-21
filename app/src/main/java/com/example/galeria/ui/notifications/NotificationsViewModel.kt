package com.example.galeria.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.galeria.data.StoreImageUrlsDao
import com.example.galeria.models.randomImageModel.Urls
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel@Inject constructor(
    private val imageDao: StoreImageUrlsDao
) : ViewModel() {
    val image = imageDao.getImages().asLiveData()

    suspend fun removeImageToDatabase(urls: Urls){
        return imageDao.delete(urls)
    }
}