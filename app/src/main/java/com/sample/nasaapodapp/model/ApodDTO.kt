package com.sample.nasaapodapp.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "apod",indices = [Index(value = ["date"], unique = true)])
data class ApodDTO(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String?,
    val url: String?,
    val isFavorite:Boolean
): Parcelable
