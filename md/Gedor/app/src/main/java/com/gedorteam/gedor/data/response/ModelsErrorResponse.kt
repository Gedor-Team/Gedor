package com.gedorteam.gedor.data.response

import com.google.gson.annotations.SerializedName

data class ModelsErrorResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("error")
	val error: String
)