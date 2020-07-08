package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName

data class School (@SerializedName("id") val id: Long,
                   @SerializedName("user") val user: User,
                   @SerializedName("name") val name: String,
                   @SerializedName("officialName") val officialName: String
)
