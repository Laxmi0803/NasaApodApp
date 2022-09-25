package com.sample.nasaapodapp.di

import android.content.Context
import androidx.room.Room
import com.sample.nasaapodapp.data.local.ApodDao
import com.sample.nasaapodapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "nasa_app.db"
        ).build()
    }

    @Provides
    fun provideDao(appDatabase: AppDatabase): ApodDao {
        return appDatabase.apodDao()
    }
}