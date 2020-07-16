package pl.e2d.clientapp.model

import java.util.*


data class Student(var id: Long? = null,
                   var user: UserEntity? = UserEntity(),
                   var schoolId: Long? = null,
                   var startEducation: Date? = null,
                   var endEducation:Date? = null
)