package com.example.galeria.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galeria.models.pageOfImagesModel.Result

class DashboardViewModel : ViewModel(){
    var image = MutableLiveData<List<Result>>()
}