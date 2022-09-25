package com.sample.nasaapodapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.nasaapodapp.MockResponseFileReader
import com.sample.nasaapodapp.data.remote.ApiService
import com.sample.nasaapodapp.repository.FakeNasaApodRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

@RunWith(JUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mockWebServer = MockWebServer()
    private lateinit var repository: FakeNasaApodRepository
    private lateinit var mockedResponse: String


    @Before
    fun init() {

        mockWebServer.start(8000)

        val baseUrl = mockWebServer.url("/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build().create(ApiService::class.java)

        repository = FakeNasaApodRepository()
    }
    @Test
    fun testApiSuccess() {

        mockedResponse = MockResponseFileReader("api-response/mock_response.json").content

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.getNasaApod("2022-09-25")}
        assertThat(response.data?.date,`is`("2022-09-25"))
        assertThat(response.data?.title, `is`("The Fairy of Eagle Nebula"))


    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}


