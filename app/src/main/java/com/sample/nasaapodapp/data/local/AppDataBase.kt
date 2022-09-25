package com.sample.nasaapodapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.nasaapodapp.model.ApodDTO

@Database(entities = [ApodDTO::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun apodDao(): ApodDao
}