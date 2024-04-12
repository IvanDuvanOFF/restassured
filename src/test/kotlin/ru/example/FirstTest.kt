package ru.example

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import ru.example.dto.LoginDto

class FirstTest {

    companion object {
        private const val BASE_URL = "http://85.192.34.140:8080"
    }

    @Test
    fun userShouldLogin() {
        val loginBody = LoginDto(
            username = "admin",
            password = "admin"
        )

        Given {
            contentType(ContentType.JSON)
            body(loginBody)
        } When {
            post("$BASE_URL/api/login")
        } Then {
            body("token", notNullValue())
        }
    }
}