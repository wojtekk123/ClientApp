package pl.e2d.clientapp.dto.masterDataEntity

import com.google.gson.annotations.SerializedName
import kotlin.Long

data class UserDto(@SerializedName("id") var id: Long? = null,
                   @SerializedName("firstName") var firstName: String? = null,
                   @SerializedName("secondName") var secondName: String? = null,
                   @SerializedName("email") var email: String? = null,
                   @SerializedName("phoneNumber") var phoneNumber: String? = null
)