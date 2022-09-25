package com.sample.nasaapodapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sample.nasaapodapp.data.local.ApodDao
import com.sample.nasaapodapp.data.local.AppDatabase
import com.sample.nasaapodapp.getOrAwaitValue
import com.sample.nasaapodapp.model.ApodDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
class ApodDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var apodDao: ApodDao

    @Before
    fun setup() {
        hiltRule.inject()
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        apodDao = appDatabase.apodDao()


    }

    @After
    fun teardown(){
        appDatabase.close()
    }

    @Test
    fun getAllApod() = runBlockingTest {
        val apodList = apodDao.getAllApod().getOrAwaitValue()
        assertThat(apodList).isNotEmpty()
    }

    @Test
    fun insertNasaApod() = runBlockingTest {
        val apodToAdd = ApodDTO(
            1, "2022-09-25",
            "The dust sculptures of the Eagle Nebula are evaporating.  As powerful starlight whittles away these cool cosmic mountains, the statuesque pillars that remain might be imagined as mythical beasts.  Featured here is one of several striking dust pillars of the Eagle Nebula that might be described as a gigantic alien fairy.   This fairy, however, is ten light years tall and spews radiation much hotter than common fire. The greater Eagle Nebula, M16, is actually a giant evaporating shell of gas and dust inside of which is a growing cavity filled with a spectacular stellar nursery currently forming an open cluster of stars.  This great pillar, which is about 7,000 light years away, will likely evaporate away in about 100,000 years.  The featured image is in scientifically re-assigned colors and was taken by the Earth-orbiting Hubble Space Telescope.",
            "https://apod.nasa.gov/apod/image/2209/FairyPillar_Hubble_3857.jpg",
            "image",
            "v1",
            "The Fairy of Eagle Nebula",
            "https://apod.nasa.gov/apod/image/2209/FairyPillar_Hubble_960.jpg",false
        )
        runBlockingTest {
            apodDao.insertAll(apodToAdd)
        }
        val apodList = apodDao.getAllApod().getOrAwaitValue()
        assertThat(apodList).isNotEmpty()
    }

    @Test
    fun deleteNasaApod() = runBlockingTest {
        val apodToAdd = ApodDTO(
            1, "2022-09-25",
            "The dust sculptures of the Eagle Nebula are evaporating.  As powerful starlight whittles away these cool cosmic mountains, the statuesque pillars that remain might be imagined as mythical beasts.  Featured here is one of several striking dust pillars of the Eagle Nebula that might be described as a gigantic alien fairy.   This fairy, however, is ten light years tall and spews radiation much hotter than common fire. The greater Eagle Nebula, M16, is actually a giant evaporating shell of gas and dust inside of which is a growing cavity filled with a spectacular stellar nursery currently forming an open cluster of stars.  This great pillar, which is about 7,000 light years away, will likely evaporate away in about 100,000 years.  The featured image is in scientifically re-assigned colors and was taken by the Earth-orbiting Hubble Space Telescope.",
            "https://apod.nasa.gov/apod/image/2209/FairyPillar_Hubble_3857.jpg",
            "image",
            "v1",
            "The Fairy of Eagle Nebula",
            "https://apod.nasa.gov/apod/image/2209/FairyPillar_Hubble_960.jpg",false
        )

        apodDao.insertAll(apodToAdd)
        apodDao.delete(apodToAdd)

        val apodList = apodDao.getAllApod().getOrAwaitValue()
        assertThat(apodList).doesNotContain(apodToAdd)
    }


}