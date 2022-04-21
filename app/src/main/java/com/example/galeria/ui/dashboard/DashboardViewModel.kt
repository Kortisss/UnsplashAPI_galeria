package com.example.galeria.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galeria.data.StoreImageUrlsDao
import com.example.galeria.models.pageOfImagesModel.Result
import com.example.galeria.models.randomImageModel.Urls
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val imageDao: StoreImageUrlsDao
) : ViewModel(){
    var image = MutableLiveData<List<Result>>()

    suspend fun postImageToDatabase(urls: Urls){
        return imageDao.insert(urls)
    }
}