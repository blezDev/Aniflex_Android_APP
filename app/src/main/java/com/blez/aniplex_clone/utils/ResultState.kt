package com.blez.aniplex_clone.utils

sealed class ResultState<T>(val data : T?= null, val message : String?= null) {
    class Loading<T>(data : T?= null) : ResultState<T>(data =  data)
    class Success<T>(data: T?) : ResultState<T>(data =  data)
    class Error<T>(message: String?,data: T?=null): ResultState<T>(message = message, data = data)
}