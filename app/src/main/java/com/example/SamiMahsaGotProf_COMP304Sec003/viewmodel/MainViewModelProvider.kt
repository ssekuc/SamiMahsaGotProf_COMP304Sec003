package com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.SamiMahsaGotProf_COMP304Sec003.model.database.LandmarkDao

class MainViewModelProvider(private val landmarkDao: LandmarkDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(landmarkDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}