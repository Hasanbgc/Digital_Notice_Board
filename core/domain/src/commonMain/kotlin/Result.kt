package com.hasan.cmpwithbookpedia.core.domain

sealed interface Results<out D,out E: ResultError> {
    data class Success<out D>(val data:D):Results<D, Nothing>
    data class Error<out E:ResultError>(val error: E):Results<Nothing,E>
}
inline fun <T,E: ResultError, R> Results<T,E>.map(map:(T) ->R):Results<R,E> {
    return when(this){
        is Results.Error ->Results.Error(error)
        is Results.Success ->Results.Success(map(data))
    }
}
fun <T,E:ResultError> Results<T,E>.asEmptyDataResult():EmptyResult<E>{
    return map { }
}
inline fun <T, E: ResultError> Results<T,E>.onSuccess(action: (T) -> Unit): Results<T,E> {
    return when(this){
        is Results.Error -> this
        is Results.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: ResultError> Results<T,E>.onError(action: (E) -> Unit): Results<T,E> {
    return when(this){
        is Results.Error -> {
            action(error)
            this
        }
        is Results.Success -> this
    }
}
sealed interface ResultError{
    val message:String
    sealed class Remote(override val message:String):ResultError{
        object REQUEST_TIMEOUT:Remote("Oops! The request took too long to complete.")
        object UNKNOWN:Remote("Unknown Error Occurred")
        object NO_INTERNET:Remote("No Internet Connection")
        object TOO_MANY_REQUESTS:Remote("Too many requests. Please try again later.")
        object SERVER_ERROR:Remote("Server error occurred. Please try again later.")
        object UNAUTHORIZED:Remote("Unauthorized access. Please log in again.")
        object SERIALIZATION_ERROR:Remote("Data serialization error occurred.")
    }
    sealed class Local(override val message:String):ResultError{
        object NOT_FOUND:Local("Data Not Found")
        object UNKNOWN:Local("Unknown Error Occurred")
        object DATABASE_ERROR:Local("Database Error Occurred")
    }
}
typealias EmptyResult<E> = Results<Unit,E>
