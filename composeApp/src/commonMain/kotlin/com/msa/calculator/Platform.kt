package com.msa.calculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform