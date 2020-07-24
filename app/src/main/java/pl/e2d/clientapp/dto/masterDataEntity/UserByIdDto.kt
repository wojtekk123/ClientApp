package pl.e2d.clientapp.dto.masterDataEntity

import com.google.gson.annotations.SerializedName

data class UserByIdDto (@SerializedName ("id") val id:Long,
                        @SerializedName ("userName") val username: String,
                        @SerializedName ("email") val email:String,
                        @SerializedName ("schoolId") val schoolId: Long)