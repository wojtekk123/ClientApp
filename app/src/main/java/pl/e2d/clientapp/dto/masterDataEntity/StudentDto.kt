package pl.e2d.clientapp.dto.masterDataEntity

import com.google.gson.annotations.SerializedName


data class StudentDto (@SerializedName("id") var id: Long? = null,
                       @SerializedName("user") var userDto:UserDto? = UserDto(),
                       @SerializedName("schoolId") var schoolId:Long? = null,
                       @SerializedName("startEducation") var startEducation: String? = null,
                       @SerializedName("endEducation") var endEducation:String? = null
)