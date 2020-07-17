package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.SingUpBodyDto
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.model.Student
import retrofit2.Call
import retrofit2.http.*


interface StudentInterface {

    @Headers("Content-Type:application/json")
    @GET("/student/all")
    fun getAllStudent(@Header("Authorization")  token: String?): Call<List<StudentDto>>

    @Headers("Content-Type:application/json")
    @PUT("/student/update/{id}")
    fun updateStudent (@Path("id") id: String?, @Body student: StudentDto, @Header ("Authorization")  token: String?): Call<StudentDto>

}