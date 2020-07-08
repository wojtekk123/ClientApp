package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName
import kotlin.Long

data class Instructor (@SerializedName("id") val id: Long,
                       @SerializedName("user") val user: User,
                       @SerializedName("schoolId") val longId: Long
)