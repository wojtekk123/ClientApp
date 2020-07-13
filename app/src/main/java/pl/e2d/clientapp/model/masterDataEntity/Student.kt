package pl.e2d.clientapp.model.masterDataEntity

import com.google.gson.annotations.SerializedName


data class Student (@SerializedName("id") var id: Long,
                    @SerializedName("user") var user:User,
                    @SerializedName("schoolId") var schoolId: Long,
                    @SerializedName("startEducation") var startEducation: String,
                    @SerializedName("endEducation") var endEducation:String
)