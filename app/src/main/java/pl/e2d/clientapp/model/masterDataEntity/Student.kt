package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName
import java.util.Date


data class Student (@SerializedName("id") val id: Long,
                    @SerializedName("user") val user:User,
                    @SerializedName("schoolId") val longId: Long,
                    @SerializedName("startEducation") val startEducation: String,
                    @SerializedName("endEducation") val endEducation:String
)