package data

import domain.ResultError
import domain.Results
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import presentation.loge

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Results<T, ResultError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Results.Error(ResultError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return Results.Error(ResultError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        return Results.Error(ResultError.Remote.UNKNOWN)
    }
    // serializer<T>() must be resolved at this call site, not inside another reified fun — see responseToResult.
    return responseToResult(response, serializer<T>())
}

suspend fun <T> responseToResult(
    response: HttpResponse,
    dataSerializer: KSerializer<T>
): Results<T, ResultError.Remote> {
    val isSuccessStatus = response.status.value in 200..299

    val envelope = try {
        NetworkJson.decodeFromString(ApiEnvelope.serializer(dataSerializer), response.bodyAsText())
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        e.loge("responseToResult parse failed for ${response.request.url}")
        return Results.Error(
            if (isSuccessStatus) ResultError.Remote.SERIALIZATION_ERROR
            else toRemoteError(response.status.value, apiStatus = null)
        )
    }

    return if (isSuccessStatus && envelope.data != null) {
        Results.Success(envelope.data)
    } else {
        "responseToResult non-success: httpStatus=${response.status.value}, apiStatus=${envelope.status}, message=${envelope.message}, error=${envelope.error}"
            .loge()
        Results.Error(toRemoteError(response.status.value, envelope.status))
    }
}

// apiStatus (business status in the body) takes priority over the HTTP status when present.
fun toRemoteError(httpStatus: Int, apiStatus: Int?): ResultError.Remote {
    return when (apiStatus ?: httpStatus) {
        400 -> ResultError.Remote.BAD_REQUEST
        401 -> ResultError.Remote.UNAUTHORIZED
        403 -> ResultError.Remote.FORBIDDEN
        404 -> ResultError.Remote.NOT_FOUND
        408 -> ResultError.Remote.REQUEST_TIMEOUT
        429 -> ResultError.Remote.TOO_MANY_REQUESTS
        in 500..599 -> ResultError.Remote.SERVER_ERROR
        else -> ResultError.Remote.UNKNOWN
    }
}