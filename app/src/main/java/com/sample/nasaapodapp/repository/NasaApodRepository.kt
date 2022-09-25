package com.sample.nasaapodapp.repository

import androidx.lifecycle.LiveData
import com.sample.nasaapodapp.common.NetworkResult
import com.sample.nasaapodapp.model.ApodDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NasaApodRepository {

    suspend fun insertApodItem(apodDTO: ApodDTO)

    suspend fun deleteApodItem(apodDTO: ApodDTO)

    suspend fun getNasaApod(date: String): NetworkResult<ApodDTO?>

    fun getLatestFetchedApod(date: String): Flow<ApodDTO?>

    fun getFavoritesApod(): LiveData<List<ApodDTO>>

}