package pl.e2d.clientapp.model

import com.google.gson.annotations.SerializedName

data class Response (@SerializedName("message") val message: String,
                     @SerializedName("token") val token: String) {
}