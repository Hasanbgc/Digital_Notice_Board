package data

import com.hasan.dnb.data.NetworkConstants
import com.hasan.dnb.session.SessionManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val NetworkJson = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    encodeDefaults = true
}

object HttpClientFactory {
    fun create(engine: HttpClientEngine, sessionManager: SessionManager): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(json = NetworkJson)
            }
            install(DefaultRequest) {
                url(NetworkConstants.BASE_URL)
                /*header("apikey",NetworkConstants.API_KEY)*/
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                sessionManager.get()?.accessToken?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 30_000L
                requestTimeoutMillis = 30_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP_Client_Logger: $message")
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}