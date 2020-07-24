package pl.e2d.clientapp.parser


import org.json.JSONArray
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import kotlin.collections.ArrayList

class ParserScheduler {

    fun jsonReservationResult(jsonString: String): ArrayList<ReservationDto> {

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
