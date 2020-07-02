package pl.e2d.clientapp.model

import com.google.gson.annotations.SerializedName

data class SingUpBody (@SerializedName("username") val username:String, @SerializedName("password") val password: String, @SerializedName("role") val role:String) {
}