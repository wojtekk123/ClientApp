package pl.e2d.clientapp.dto.scheduler

import com.google.gson.annotations.SerializedName

 data class ReservationDto (@SerializedName("id") var id:Long,
                            @SerializedName("instructorId") var instructorId:Long,
                            @SerializedName("studentId") var studnetId:Long,
                            @SerializedName("carId") var carId:Long,
                            @SerializedName("rideDataFrom") var rideDateForm:String,
                            @SerializedName("rideDateTo") var rideDateTo:String,
                            @SerializedName("typeReservation") var typeReservation:String)