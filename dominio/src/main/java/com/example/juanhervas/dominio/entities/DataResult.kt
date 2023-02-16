package com.example.juanhervas.dominio.entities

sealed class DataResult<out T> {
    data class Success<out T>(
        val data: T
    ) : DataResult<T>()

    open class Error(
        val code: String? = "",
        val message: String? = null,
        val cause: String? = "",
        val isGlobalEvent: Boolean
    ) : DataResult<Nothing>()
}