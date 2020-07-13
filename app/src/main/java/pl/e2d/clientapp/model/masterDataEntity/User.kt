package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName
import kotlin.Long

data class User(@SerializedName("id") var id: Long,
                @SerializedName("firstName") var firstName: String,
                @SerializedName("secondName") var secondName: String,
                @SerializedName("email") var email: String,
                @SerializedName("phoneNumber") var phoneNumber: String
)