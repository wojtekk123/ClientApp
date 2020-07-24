package pl.e2d.clientapp.parser

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.masterDataEntity.UserDto
import pl.e2d.clientapp.model.Student
import java.time.ZoneId
import kotlin.collections.ArrayList
import java.util.*


class ParserMaster {

    fun jsonStudentResult(jsonString: String?): ArrayList<StudentDto> {

        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<StudentDto>()
        var i = 0
        while (i < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                StudentDto(
                    jsonObject.getLong("id"),
                    UserDto(
                        jsonObject.getJSONObject("user").getLong("id"),
                        jsonObject.getJSONObject("user").getString("firstName"),
                        jsonObject.getJSONObject("user").getString("secondName"),
                        jsonObject.getJSONObject("user").getString("email"),
                        jsonObject.getJSONObject("user").getString("phoneNumber")
                    ),
                    jsonObject.getLong("schoolId"),
                    jsonObject.getString("startEducation").toString(),
                    jsonObject.getString("endEducation")
                )
            )
            i++
        }
        return list
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun parsToDisplayData(student: Student): List<String?> {

        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date.from(student.startEducation?.atZone(ZoneId.systemDefault())!!.toInstant())
        val startDateLocalTime:String = (calendar as GregorianCalendar).toZonedDateTime().toLocalDate().toString()
        calendar.time = Date.from(student.endEducation?.atZone(ZoneId.systemDefault())!!.toInstant())
        val endDateLocalTime:String = calendar.toZonedDateTime().toLocalDate().toString()

        return listOf(student.user?.firstName,
                      student.user?.secondName,
                      student.user?.email,
                      student.user?.phoneNumber,
                      student.schoolId.toString(),
                      startDateLocalTime,
                      endDateLocalTime
           )
    }
}

