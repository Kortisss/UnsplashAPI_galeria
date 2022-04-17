package com.example.galeria.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    //simple string
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    //get, set url
    private val imageURL = MutableLiveData<String>()
    fun setURL(s: String){
        imageURL.value = s
    }
    fun getURL(): LiveData<String> {
        return imageURL
    }
}