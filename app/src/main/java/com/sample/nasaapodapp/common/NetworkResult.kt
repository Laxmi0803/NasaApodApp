package com.sample.nasaapodapp.common

sealed class NetworkResult <T> (
    val data : T ? =  null,
    val error : String ?= null
){
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Loading<T>(data: T? = null) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
}
