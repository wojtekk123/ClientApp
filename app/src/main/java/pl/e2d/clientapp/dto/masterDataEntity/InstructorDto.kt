package pl.e2d.clientapp.dto.masterDataEntity

import com.google.gson.annotations.SerializedName
import kotlin.Long

data class InstructorDto (@SerializedName("id") val id: Long? = null,
                          @SerializedName("user") val userDto: UserDto? = null,
                          @SerializedName("schoolId") val longId: Long? = null
)