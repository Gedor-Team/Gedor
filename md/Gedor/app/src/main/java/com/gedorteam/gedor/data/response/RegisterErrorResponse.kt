package com.gedorteam.gedor.data.response

import com.google.gson.annotations.SerializedName

data class RegisterErrorResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)
