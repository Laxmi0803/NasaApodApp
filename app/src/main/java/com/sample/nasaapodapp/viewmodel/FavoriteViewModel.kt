package com.sample.nasaapodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.nasaapodapp.model.ApodDTO
import com.sample.nasaapodapp.repository.NasaApodRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val nasaApodRepository: NasaApodRepositoryImpl
) : ViewModel() {

    fun getAllSavedApod() = nasaApodRepository.getFavoritesApod()

    fun onFavoriteListSwiped(apodDTO: ApodDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            nasaApodRepository.deleteApodItem(apodDTO)
        }
    }

    fun onUndoDeleteClick(apodDTO: ApodDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            nasaApodRepository.insertApodItem(apodDTO)
        }
    }
}