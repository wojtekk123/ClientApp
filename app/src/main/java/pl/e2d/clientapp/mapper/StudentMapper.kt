package pl.e2d.clientapp.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.masterDataEntity.UserDto
import pl.e2d.clientapp.model.Student
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun mapToDto(student: Student): StudentDto {

    return StudentDto(
        student.id,
        UserDto(
            student.user?.id,
            student.user?.firstName,
            student.user?.secondName,
            student.user?.email,
            student.user?.phoneNumber
        ),
        student.id,
        student.startEducation.toString(),
        student.endEducation.toString()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun mapToModel(student: StudentDto): Student {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSX", Locale.GERMAN)

    return Student(
        student.id,
        UserDto(
            student.userDto?.id,
            student.userDto?.firstName,
            student.userDto?.secondName,
            student.userDto?.email,
            student.userDto?.phoneNumber
        ),
        student.schoolId,
        LocalDateTime.parse(student.startEducation, formatter),
        LocalDateTime.parse(student.endEducation, formatter)
    )
}
