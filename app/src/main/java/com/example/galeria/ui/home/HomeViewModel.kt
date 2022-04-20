package com.example.galeria.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.galeria.data.StoreImageUrlsDao
import com.example.galeria.models.pageOfImagesModel.Result
import com.example.galeria.models.randomImageModel.Image
import com.example.galeria.models.randomImageModel.Urls
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val imageDao: StoreImageUrlsDao
) : ViewModel(){

    //get, set url
    private val imageURL = MutableLiveData<String>()
    fun setURL(s: String){
        imageURL.value = s
    }
    fun getURL(): LiveData<String> {
        return imageURL
    }
    //get,set ImageObj
    private val imageObj = MutableLiveData<Image>()
    fun setObj(s: Image){
        imageObj.value = s
    }
    fun getObj(): LiveData<Image> {
        return imageObj
    }
    suspend fun postImageToDatabase(urls: Urls){
        return imageDao.insert(urls)
    }
}