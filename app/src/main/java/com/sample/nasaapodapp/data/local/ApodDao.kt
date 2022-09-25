package com.sample.nasaapodapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

import com.sample.nasaapodapp.model.ApodDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apod: ApodDTO?)

    @Delete
    suspend fun delete(apod: ApodDTO)

    @Query("Select * from apod order by date DESC ")
    fun getAllApod(): LiveData<List<ApodDTO>>

    @Query("SELECT * FROM apod WHERE date=:dateValue")
    fun getApodByDate(dateValue:String): Flow<ApodDTO?>

    @Query("SELECT * FROM apod ORDER BY id DESC LIMIT 1")
    fun getLastApod(): Flow<ApodDTO?>





}