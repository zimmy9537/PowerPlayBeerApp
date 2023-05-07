package com.powerplay.zimmy.powerplaybeerapp.network

sealed class ResultData<out T> {
    object Loading: ResultData<Nothing>()
    data class Success<out T>(val data: T? = null): ResultData<T>()
    data class Failed(val message: String? = null): ResultData<Nothing>()
}