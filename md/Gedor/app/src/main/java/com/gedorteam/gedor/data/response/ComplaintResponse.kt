package com.gedorteam.gedor.data.response

import com.google.gson.annotations.SerializedName

data class ComplaintResponse(

	@field:SerializedName("ComplaintResponse")
	val complaintResponse: List<ComplaintResponseItem?>? = null
)

data class ComplaintResponseItem(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("complaintID")
	val complaintID: Int? = null,

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
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
