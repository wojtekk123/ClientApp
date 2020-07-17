package pl.e2d.clientapp.model

import com.google.gson.annotations.SerializedName
import pl.e2d.clientapp.dto.masterDataEntity.UserDto
import java.time.LocalDateTime
import java.util.*


data class Student(@SerializedName("id") var id: Long? = null,
                   @SerializedName("user") var user: UserDto? = UserDto(),
                   @SerializedName("schoolId")var schoolId: Long? = null,
                   @SerializedName("startEducation")var startEducation: LocalDateTime? = null,
                   @SerializedName("endEducation") var endEducation:LocalDateTime? = null
)