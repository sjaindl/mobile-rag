package com.sjaindl.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform