package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName
import kotlin.Long

data class Car (@SerializedName("id") val id: Long,
                @SerializedName("schoolId") val longId: Long,
                @SerializedName("model") val model: String,
                @SerializedName("brand") val brand: String,
                @SerializedName("registrationNumber") val registrationNumber: String)