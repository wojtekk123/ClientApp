package pl.e2d.clientapp.dto.masterDataEntity

import com.google.gson.annotations.SerializedName

data class SchoolDto (@SerializedName("id") val id: Long,
                      @SerializedName("user") val userDto: UserDto,
                      @SerializedName("name") val name: String,
                      @SerializedName("officialName") val officialName: String
)
