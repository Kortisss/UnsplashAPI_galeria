package com.example.galeria.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galeria.data.StoreImageUrlsDao
import com.example.galeria.models.pageOfImagesModel.PageModel
import com.example.galeria.models.pageOfImagesModel.Result
import com.example.galeria.models.randomImageModel.Urls
import com.example.galeria.ui.home.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "DashboardViewModel"
private const val clientId = "zxQxVwX0NyRLHKKXGXyNiXIQzY0Q2iT6esxgxi2MJOo"
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val imageDao: StoreImageUrlsDao
) : ViewModel(){
    var image = MutableLiveData<List<Result>>()
    private var page = MutableLiveData<Int>().apply { value = 1 }
    var loading = MutableLiveData<Boolean>().apply { postValue(false)}
    var queryString = MutableLiveData<String>()
    var queryColorOption = MutableLiveData<String>().apply { postValue("white")}
    var queryOrderByOption = MutableLiveData<String>().apply { postValue("relevant")}

    fun returnImages(){
        viewModelScope.launch {
            loading.value = true
            val a = RetrofitInstance.API.getListOfImages(
                clientId,
                page.value.toString(),
                queryString.value.toString(),
                queryOrderByOption.value.toString(),
                queryColorOption.value.toString())

            val listOfImages: PageModel = a.body()!!
            image.value = listOfImages.results
            Log.d(TAG,"Triggered request")
            loading.value = false
        }
    }
    fun nextPage(){
        viewModelScope.launch {
            val query = let{queryString.value.toString()}
            val colorOption = let{queryColorOption.value.toString()}
            val orderByOption = let{queryOrderByOption.value.toString()}
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")
            delay(1000)
            if (page.value!! > 1){
                val result = RetrofitInstance.API.getListOfImages(clientId,page.value.toString(),query,orderByOption,colorOption)
                Log.d(TAG, "search: appending")
                result.body()?.let { appendImages(it.results) }
            }
            loading.value = false
        }
    }
    private fun appendImages(images: List<Result>){
        val current = ArrayList(images)
        current.addAll(images)
        this.image.value = this.image.value?.plus(current)
    }
    private fun incrementPage(){
        page.value = page.value?.plus(1)
    }
    suspend fun postImageToDatabase(urls: Urls){
        return imageDao.insert(urls)
    }
}