package pl.e2d.clientapp.parser

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import pl.e2d.clientapp.model.Student
import pl.e2d.clientapp.model.UserEntity
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import java.util.*


class ParserMaster {

    fun jsonStudentResult(jsonString: String?): ArrayList<Student> {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSX", Locale.GERMAN)

        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<Student>()
        var i = 0
        while (i < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                Student(
                    jsonObject.getLong("id"),
                    UserEntity(
                        jsonObject.getJSONObject("user").getLong("id"),
                        jsonObject.getJSONObject("user").getString("firstName"),
                        jsonObject.getJSONObject("user").getString("secondName"),
                        jsonObject.getJSONObject("user").getString("email"),
                        jsonObject.getJSONObject("user").getString("phoneNumber")
                    ),
                    jsonObject.getLong("schoolId"),
                    sdf.parse(jsonObject.getString("startEducation")),
                    sdf.parse(jsonObject.getString("endEducation"))
                )
            )
            i++
        }
        return list
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun jsonStudentPopUp(student: Student): List<String?> {

        val calendar: Calendar = Calendar.getInstance()
        calendar.time = student.startEducation
        val startDateLocalTime:String = (calendar as GregorianCalendar).toZonedDateTime().toLocalDate().toString()
        calendar.time = student.endEducation
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

