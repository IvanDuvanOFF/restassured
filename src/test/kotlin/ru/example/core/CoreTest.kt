package ru.example.core

abstract class CoreTest {
    companion object {
        const val BASE_URL = "http://85.192.34.140:8080"
    }

    abstract val endpoint: String
}