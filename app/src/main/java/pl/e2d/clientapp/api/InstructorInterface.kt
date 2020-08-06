package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.masterDataEntity.InstructorDto
import pl.e2d.clientapp.dto.masterDataEntity.UserByIdDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface InstructorInterface {


    @Headers("Content-Type:application/json")
    @GET("/ride/getInstructorById/{id}")
    fun getInstructorById(@Path("id") id: Long?, @Header("Authorization")  token: String?): Call<UserByIdDto>

}