package com.hasan.dnb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform