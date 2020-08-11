package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.scheduler.ReservationDto
import retrofit2.Call
import retrofit2.http.*

interface ReservationInterface {

    @Headers("Content-Type:application/json")
    @GET("/reservation/all")
    fun getAllReservation(@Header("Authorization")  token: String?): Call<List<ReservationDto>>

    @Headers("Content-Type:application/json")
    @DELETE("/reservation/delete/{id}")
    fun deleteReservation (@Path("id") id: Long? ,@Header("Authorization")  token: String?): Call<ReservationDto>

    @Headers("Content-Type:application/json")
    @PATCH("/reservation/approve/{id}")
    fun approveReservation (@Path("id") id: Long? ,@Header("Authorization")  token: String?): Call<ReservationDto>

    @Headers("Content-Type:application/json")
    @PATCH("/reservation/decline/{id}")
    fun declineReservation (@Path("id") id: Long? ,@Header("Authorization")  token: String?): Call<ReservationDto>
}