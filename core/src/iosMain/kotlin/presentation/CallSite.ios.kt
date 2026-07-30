package presentation

// kotlin.Throwable on Native doesn't expose structured stack frames (className/fileName/lineNumber)
// the way java.lang.Throwable does, so we fall back to the raw trace text as a best effort.
internal actual fun callerCallSite(): CallSite {
    val callerFrame = Throwable().stackTraceToString()
        .lineSequence()
        .drop(2)
        .firstOrNull()
        ?.trim()
        ?: "unknown"

    return CallSite(
        className = "iOS",
        fileName = "unknown",
        lineNumber = 0,
        methodName = callerFrame
    )
}
