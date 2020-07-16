package pl.e2d.clientapp.dto

import com.google.gson.annotations.SerializedName

data class SingUpBodyDto (@SerializedName("username") val username:String,
                          @SerializedName("password") val password: String,
                          @SerializedName("role") val role:String) {
}