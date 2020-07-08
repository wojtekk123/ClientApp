package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName
import kotlin.Long

data class User(@SerializedName("id") val id: Long,
                @SerializedName("firstName") val firstName: String,
                @SerializedName("secondName") val secondName: String,
                @SerializedName("email") val email: String,
                @SerializedName("phoneNumber") val phoneNumber: String
)