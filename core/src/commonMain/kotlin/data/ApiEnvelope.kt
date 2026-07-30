package data

import kotlinx.serialization.Serializable

@Serializable
data class ApiEnvelope<T>(
    val status: Int? = null,
    val message: String? = null,
    val data: T? = null,
    val error: String? = null
)
