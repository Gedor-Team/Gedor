package com.gedorteam.gedor.data.response

import com.google.gson.annotations.SerializedName

data class ComplaintResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class DataItem(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("complaint")
	val complaint: String? = null,

	@field:SerializedName("govID")
	val govID: Int? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("userID")
	val userID: Int? = null,

	@field:SerializedName("kabupaten")
	val kabupaten: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
