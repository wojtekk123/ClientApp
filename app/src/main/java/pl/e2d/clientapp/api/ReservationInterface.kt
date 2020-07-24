package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ReservationInterface {

    @Headers("Content-Type:application/json")
    @GET("/reservation/all")
    fun getAllReservation(@Header("Authorization")  token: String?): Call<List<ReservationDto>>
}