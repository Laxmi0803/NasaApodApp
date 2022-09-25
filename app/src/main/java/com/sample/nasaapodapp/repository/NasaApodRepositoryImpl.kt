package com.sample.nasaapodapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.sample.nasaapodapp.common.NetworkResult
import com.sample.nasaapodapp.data.local.ApodDao
import com.sample.nasaapodapp.data.remote.ApiService
import com.sample.nasaapodapp.model.ApodDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NasaApodRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    private val apodDao: ApodDao
) : NasaApodRepository {
    override suspend fun insertApodItem(apodDTO: ApodDTO) = apodDao.insertAll(apodDTO)

    override suspend fun deleteApodItem(apodDTO: ApodDTO) = apodDao.delete(apodDTO)


    override suspend fun getNasaApod(date: String): NetworkResult<ApodDTO?> {
        return try {
            val dateValue  = if (date.isNullOrEmpty()) SimpleDateFormat("yyyy-MM-dd").format(Date()) else date
            val response = apiService.getApodDetails(dateValue)
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    return NetworkResult.Success(resultResponse)
                }
            }
            return NetworkResult.Error(response.message())

        } catch(e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            NetworkResult.Error("Couldn't reach the server. Check your internet connection")
        }
    }

    override fun getLatestFetchedApod(date: String): Flow<ApodDTO?> {
          return  if (date.isNullOrEmpty())
              apodDao.getLastApod()
            else
              apodDao.getApodByDate(date)
    }

    override fun getFavoritesApod() = apodDao.getAllApod()


}