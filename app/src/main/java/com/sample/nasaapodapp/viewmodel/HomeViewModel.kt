package com.sample.nasaapodapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.nasaapodapp.common.NetworkResult
import com.sample.nasaapodapp.common.NetworkUtil.Companion.hasInternetConnection
import com.sample.nasaapodapp.model.ApodDTO
import com.sample.nasaapodapp.repository.NasaApodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var nasaApodRepository: NasaApodRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _nasaApod = MutableLiveData<NetworkResult<ApodDTO?>>()
    val nasaApod: LiveData<NetworkResult<ApodDTO?>> = _nasaApod

    fun getNasaApod(date: String) = viewModelScope.launch {

        try {
            if (hasInternetConnection(context)) {

                val response = nasaApodRepository.getNasaApod(date)
                _nasaApod.postValue(response)

            } else {
                nasaApodRepository.getLatestFetchedApod(date)
                    .catch { e ->
                        _nasaApod.postValue(NetworkResult.Error(e.message.toString()))
                    }
                    .collect {
                        _nasaApod.postValue(NetworkResult.Success(it))
                    }
            }
        } catch (ex: IOException) {
            _nasaApod.postValue(NetworkResult.Error("Network Failure"))
        }

    }

    fun saveApod(apod: ApodDTO?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (apod != null) {
                nasaApodRepository.insertApodItem(apod)
            }

        }
    }
}
