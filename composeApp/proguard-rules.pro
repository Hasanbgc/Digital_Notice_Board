## ===== Attributes =====
  -keepattributes Signature
  -keepattributes *Annotation*
  -keepattributes EnclosingMethod

-dontwarn java.beans.**
  -dontwarn java.lang.management.**
  -dontwarn javax.naming.**
  -dontwarn org.slf4j.**
  -dontwarn okhttp3.internal.sse.**
  -dontwarn io.ktor.**
  -dontwarn io.ktor.utils.**

  #-keep class io.ktor.** { *; }
  -keep class io.ktor.utils.** { *; }
