package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.masterDataEntity.UserByIdDto
import retrofit2.Call
import retrofit2.http.*


interface StudentInterface {

    @Headers("Content-Type:application/json")
    @GET("/student/all")
    fun getAllStudent(@Header("Authorization")  token: String?): Call<List<StudentDto>>

    @Headers("Content-Type:application/json")
    @GET("/ride/getStudentById/{id}")
    fun getStudentById(@Path("id") id: Long?, @Header("Authorization")  token: String?): Call<UserByIdDto>

    @Headers("Content-Type:application/json")
    @PUT("/student/update/{id}")
    fun updateStudent (@Path("id") id: Long?, @Body student: StudentDto, @Header ("Authorization")  token: String?): Call<StudentDto>

    @Headers("Content-Type:application/json")
    @DELETE("/student/delete/{id}")
    fun deletedStudent (@Path("id") id: Long?, @Header ("Authorization")  token: String?): Call<StudentDto>

    @Headers("Content-Type:application/json")
    @POST("/student/add")
    fun addStudent (@Body student: StudentDto, @Header ("Authorization")  token: String?): Call<StudentDto>

}