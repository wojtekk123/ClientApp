package pl.e2d.clientapp.model

import com.google.gson.annotations.SerializedName
import pl.e2d.clientapp.enums.ReservationType
import java.time.LocalDateTime

data class Reservation (@SerializedName("id") var id:Long,
                        @SerializedName("instructorId") var instructorId:Long,
                        @SerializedName("studentId") var studentId:Long,
                        @SerializedName("carId") var carId:Long,
                        @SerializedName("rideDataFrom") var rideDateForm:LocalDateTime,
                        @SerializedName("rideDateTo") var rideDateTo:LocalDateTime,
                        @SerializedName("typeReservation") var typeReservation:ReservationType)