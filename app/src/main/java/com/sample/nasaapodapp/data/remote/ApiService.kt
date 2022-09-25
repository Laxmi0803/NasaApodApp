package com.sample.nasaapodapp.data.remote

import com.sample.nasaapodapp.BuildConfig
import com.sample.nasaapodapp.model.ApodDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("planetary/apod?api_key=${BuildConfig.API_KEY}")
    suspend fun getApodDetails(@Query("date") date: String): Response<ApodDTO>
}