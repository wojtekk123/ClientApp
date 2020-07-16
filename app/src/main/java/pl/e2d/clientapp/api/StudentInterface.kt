package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.SingUpBodyDto
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import retrofit2.Call
import retrofit2.http.*


interface StudentInterface {

    @Headers("Content-Type:application/json")
    @GET("/student/all")
    fun getAllStudent(@Header("Authorization")  token: String?): Call<List<StudentDto>>

    @Headers("Content-Type:application/json")
    @PUT("/student/update/{id}")
    fun updateStudent (@Path("id") id: String?, @Body studentDto: StudentDto, @Header ("Authorization")  token: String?): Call<SingUpBodyDto>

}