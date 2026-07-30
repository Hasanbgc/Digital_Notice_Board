package presentation

internal actual fun callerCallSite(): CallSite {
    val element = Throwable().stackTrace[2]
    return CallSite(
        className = element.className,
        fileName = element.fileName ?: "Unknown",
        lineNumber = element.lineNumber,
        methodName = element.methodName
    )
}
