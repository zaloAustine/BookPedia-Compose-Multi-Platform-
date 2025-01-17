package org.example.project.core.data


import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.example.project.core.domain.DataError
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): org.example.project.core.domain.Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return org.example.project.core.domain.Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: UnresolvedAddressException) {
        return org.example.project.core.domain.Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return org.example.project.core.domain.Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): org.example.project.core.domain.Result<T, DataError.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                org.example.project.core.domain.Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                org.example.project.core.domain.Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> org.example.project.core.domain.Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> org.example.project.core.domain.Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> org.example.project.core.domain.Result.Error(DataError.Remote.SERVER)
        else -> org.example.project.core.domain.Result.Error(DataError.Remote.UNKNOWN)
    }
}