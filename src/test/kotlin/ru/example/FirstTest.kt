package ru.example

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matcher
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.example.core.CoreTest
import ru.example.dto.LoginDto

class FirstTest : CoreTest() {

    override val endpoint: String
        get() = "/api/login"

    companion object {

        private lateinit var spec: RequestSpecification

        @JvmStatic
        @BeforeAll
        fun setUp() {
            spec = RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build()
        }
    }

    @Test
    fun userShouldLogin() {
        loginUser()
    }

    @Test
    fun userCannotLogin() {
        val invalidLogin = LoginDto(
            username = "admin",
            password = "invalid password"
        )

        Given {
            addGiven(invalidLogin)
        } When {
            post(endpoint)
        } Then {
            expect(
                401,
                "path" to equalTo("/api/login"),
                "status" to equalTo(401),
                "error" to equalTo("Unauthorized")
            )
        }
    }

    private fun loginUser(username: String = "admin", password: String = "admin"): LoginDto =
        LoginDto(username, password).apply {
            Given {
                addGiven(this@apply)
            } When {
                post(endpoint)
            } Then {
                expect(200, "token" to notNullValue())
            }
        }

    private fun RequestSpecification.addGiven(body: Any): RequestSpecification = apply {
        spec(spec)
        body(body)
    }

    private fun ValidatableResponse.expect(statusCode: Int, vararg values: Pair<String, Matcher<Any>>) {
        statusCode(statusCode)

        values.forEach { body(it.first, it.second) }
    }
}