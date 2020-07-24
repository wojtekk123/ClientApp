package pl.e2d.clientapp.parser

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import pl.e2d.clientapp.model.Reservation
import pl.e2d.clientapp.model.Student
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class ParserScheduler {

    fun jsonReservationResult(jsonString: String): ArrayList<ReservationDto> {

        val calendar: Calendar = Calendar.getInstance()
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<ReservationDto>()
        var i = 0
        while (i < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                ReservationDto(
                    jsonObject.getLong("id"),
                    jsonObject.getLong("instructorId"),
                    jsonObject.getLong("studentId"),
                    jsonObject.getLong("carId"),
                    jsonObject.getString("rideDataFrom"),
                    jsonObject.getString("rideDateTo"),
                    jsonObject.getString("typeReservation")
                )
            )
            i++
        }
        return list
    }

}
