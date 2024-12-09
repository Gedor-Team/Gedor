package com.gedorteam.gedor.data.response

import com.google.gson.annotations.SerializedName

data class SpamCheckModelResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("predictions")
	val predictions: Boolean
)

data class PredictCategoryModelResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("predictions")
	val predictions: String
)
