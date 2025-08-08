import com.hasan.cmpwithbookpedia.core.domain.ResultError
import com.hasan.cmpwithbookpedia.core.domain.Results
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun  <reified T>safeCall(
    execute:() -> HttpResponse
):Results<T, ResultError.Remote> {
    val result = try {
        execute()
    }catch (e: SocketTimeoutException) {
        return Results.Error(ResultError.Remote.REQUEST_TIMEOUT)
    }catch (e: UnresolvedAddressException){
        return Results.Error(ResultError.Remote.NO_INTERNET)
    }catch (e: Exception){
        coroutineContext.ensureActive()
        return Results.Error(ResultError.Remote.UNKNOWN)
    }
    println("Result_Response: $result")
    return responseToResult(result)
}


suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Results<T, ResultError.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Results.Success(response.body<T>())
            }catch (e: NoTransformationFoundException){
                Results.Error(ResultError.Remote.SERIALIZATION_ERROR)
            }
        }
        408 -> Results.Error(ResultError.Remote.REQUEST_TIMEOUT)
        429 -> Results.Error(ResultError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Results.Error(ResultError.Remote.SERVER_ERROR)
        else -> Results.Error(ResultError.Remote.UNKNOWN)
    }

}