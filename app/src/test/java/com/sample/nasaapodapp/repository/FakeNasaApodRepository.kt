package com.sample.nasaapodapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.sample.nasaapodapp.common.NetworkResult
import com.sample.nasaapodapp.model.ApodDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

class  FakeNasaApodRepository():NasaApodRepository {

    private var apodLists = mutableListOf<ApodDTO>()

    private val observableApodItems = MutableLiveData<List<ApodDTO>>(apodLists)

private val apod = MutableLiveData<ApodDTO?>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableApodItems.postValue(apodLists)
    }
    override suspend fun insertApodItem(apodDTO: ApodDTO) {
        apodLists.add(apodDTO)
        refreshLiveData()
    }

    override suspend fun deleteApodItem(apodDTO: ApodDTO) {
        apodLists.remove(apodDTO)
        refreshLiveData()
    }

    override suspend fun getNasaApod(date: String): NetworkResult<ApodDTO?> {
        return if(shouldReturnNetworkError) {
            NetworkResult.Error("Error", null)
        } else {
            NetworkResult.Success(ApodDTO(
                1, "2022-09-25",
                "The dust sculptures of the Eagle Nebula are evaporating.  As powerful starlight whittles away these cool cosmic mountains, the statuesque pillars that remain might be imagined as mythical beasts.  Featured here is one of several striking dust pillars of the Eagle Nebula that might be described as a gigantic alien fairy.   This fairy, however, is ten light years tall and spews radiation much hotter than common fire. The greater Eagle Nebula, M16, is actually a giant evaporating shell of gas and dust inside of which is a growing cavity filled with a spectacular stellar nursery currently forming an open cluster of stars.  This great pillar, which is about 7,000 light years away, will likely evaporate away in about 100,000 years.  The featured image is in scientifically re-assigned colors and was taken by the Earth-orbiting Hubble Space Telescope.",
                "https://apod.nasa.gov/apod/image/2209/FairyPillar_Hubble_3857.jpg",
                "image",
                "v1",
                "The Fairy of Eagle Nebula",
                "https://apod.nasa.gov/apod/image/2209/FairyPillar_Hubble_960.jpg",false
            ))
        }
    }

    override fun getLatestFetchedApod(date: String): Flow<ApodDTO?> {

return apod.asFlow()
    }

    override fun getFavoritesApod(): LiveData<List<ApodDTO>> {
      return observableApodItems
    }
}