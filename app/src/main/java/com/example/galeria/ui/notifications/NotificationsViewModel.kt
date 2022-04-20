package com.example.galeria.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.galeria.data.StoreImageUrlsDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel@Inject constructor(
    imageDao: StoreImageUrlsDao
) : ViewModel() {
    val image = imageDao.getImages().asLiveData()
}