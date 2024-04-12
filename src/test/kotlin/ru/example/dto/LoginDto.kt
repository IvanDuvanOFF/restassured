package ru.example.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginDto(

	@field:JsonProperty("username")
	val username: String,

	@field:JsonProperty("password")
	val password: String
)
