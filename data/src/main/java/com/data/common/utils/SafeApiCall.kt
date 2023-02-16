package com.data.common.utils

import com.data.common.entities.SingleResponse
import com.example.juanhervas.dominio.entities.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

suspend fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>
): DataResult<T> {
    return withContext(dispatcher) {
        try {
            val result = apiCall()
            determinateSuccessResponse(result)
        } catch (ex: Exception) {
            DataResult.Error(code = "", message = ex.message, "", isGlobalEvent = false)
        }
    }
}

suspend fun <T : Any> determinateSuccessResponse(
    result: Response<T>
): DataResult<T> {
    val body = result.body()!!
    return if (result.isSuccessful) {
        DataResult.Success(
            data = body
        )
    } else {
        DataResult.Error(code = "", message = body.toString(), "", isGlobalEvent = false)
    }
}

fun <I> DataResult<I>.toUnit(): DataResult<Unit> {
    return this.map { }
}

fun <I> DataResult<SingleResponse<I>>.single(): DataResult<I> {
    return this.map {
        it.detail
    }
}

fun <I, O> DataResult<I>.map(mapper: (I) -> O): DataResult<O> {
    return when (this) {
        is DataResult.Success -> DataResult.Success(data = mapper(data))
        is DataResult.Error -> this
    }
}

fun <I, O> DataResult<SingleResponse<I>>.mapFlatDetail(block: (I) -> O): DataResult<O> {
    return this.map {
        it.map(block).detail
    }
}

fun <I, O> SingleResponse<I>.map(block: (I) -> O): SingleResponse<O> {
    return SingleResponse(
        code = code,
        description = description,
        detail = detail.let(block)
    )
}

inline fun <T> DataResult<T>.ifSuccessful(block: (T) -> Unit): DataResult<T> {
    if (isSuccessful()) {
        block(data)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
fun <T> DataResult<T>.isSuccessful(): Boolean {
    contract {
        returns(true) implies (this@isSuccessful is DataResult.Success)
        returns(false) implies (this@isSuccessful is DataResult.Error)
    }
    return this is DataResult.Success
}

