package com.dicoding.cindy.storyapp.data.response.signup

import com.google.gson.annotations.SerializedName

data class SignupResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
