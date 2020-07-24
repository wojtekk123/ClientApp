package pl.e2d.clientapp.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto (@SerializedName("message") val message: String,
                        @SerializedName("token") val token: String)
