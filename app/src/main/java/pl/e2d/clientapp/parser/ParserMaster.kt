package pl.e2d.clientapp.parser

import org.json.JSONArray
import pl.e2d.clientapp.model.masterDataEntity.Student
import pl.e2d.clientapp.model.masterDataEntity.User


class ParserMaster {

     fun jsonStudentResult(jsonString: String?) :ArrayList<Student>{
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<Student>()
        var i = 0
        while (i < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                Student(
                    i.toLong(),
                    User(
                        jsonObject.getJSONObject("user").getLong("id"),
                        jsonObject.getJSONObject("user").getString("firstName"),
                        jsonObject.getJSONObject("user").getString("secondName"),
                        jsonObject.getJSONObject("user").getString("email"),
                        jsonObject.getJSONObject("user").getString("phoneNumber")),
                    jsonObject.getLong("schoolId"),
                    jsonObject.getString("startEducation"),
                    jsonObject.getString("endEducation")
                )
            )
            i++
        }
        return list
    }

    fun studentParsPopup(toJson: String?)  {



    }


}