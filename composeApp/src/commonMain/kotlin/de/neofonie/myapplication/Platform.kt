package de.neofonie.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform