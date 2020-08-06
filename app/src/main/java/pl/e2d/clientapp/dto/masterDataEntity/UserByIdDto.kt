package pl.e2d.clientapp.dto.masterDataEntity

import com.google.gson.annotations.SerializedName

data class UserByIdDto (@SerializedName ("id") val id:Long? = null,
                        @SerializedName ("userName") val username: String? = null,
                        @SerializedName ("email") val email:String? = null,
                        @SerializedName ("schoolId") val schoolId: Long? = null)