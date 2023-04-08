package com.example.SamiMahsaGotProf_COMP304Sec003.viewmodel

import androidx.lifecycle.ViewModel
import com.example.SamiMahsaGotProf_COMP304Sec003.model.Landmark
import com.example.SamiMahsaGotProf_COMP304Sec003.model.LandmarkType
import com.example.SamiMahsaGotProf_COMP304Sec003.model.database.LandmarkDao

class MainViewModel(private val landmarkDao: LandmarkDao) : ViewModel() {

    private var _selectedType: LandmarkType? = null
    val selectedType: LandmarkType?
        get() = _selectedType

    private var _selectedLandmark: Landmark? = null
    var selectedLandmark: Landmark? = null
        get() = _selectedLandmark
        set(value) {
            field = value
            _selectedLandmark = value
        }

    fun getLandmarksByType(type: LandmarkType) =
        landmarkDao.getAllLandmarksByType(type.name)

    fun getLandmarkTypes(): List<String> {
        val result = mutableListOf<String>()
        result.add(LandmarkType.MUSEUMS.name)
        result.add(LandmarkType.ATTRACTIONS.name)
        result.add(LandmarkType.OLD_BUILDING.name)
        return result
    }

    fun setSelectedType(selectedType: String) {
        _selectedType = when (selectedType) {
            LandmarkType.MUSEUMS.name -> LandmarkType.MUSEUMS
            LandmarkType.OLD_BUILDING.name -> LandmarkType.OLD_BUILDING
            LandmarkType.ATTRACTIONS.name -> LandmarkType.ATTRACTIONS
            else -> null
        }
    }
}