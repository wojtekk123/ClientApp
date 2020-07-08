package pl.e2d.clientapp.model

import com.google.gson.annotations.SerializedName

data class SignInBody (@SerializedName("username") val username:String,
                       @SerializedName("password") val password:String){





}