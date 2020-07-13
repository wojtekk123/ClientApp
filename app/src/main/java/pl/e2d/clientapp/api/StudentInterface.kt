package pl.e2d.clientapp.api

import pl.e2d.clientapp.model.SingUpBody
import pl.e2d.clientapp.model.masterDataEntity.Student
import retrofit2.Call
import retrofit2.http.*


interface StudentInterface {

    @Headers("Content-Type:application/json")
    @GET("/student/all")
    fun getAllStudent(@Header("Authorization")  token: String?): Call<List<Student>>

    @Headers("Content-Type:application/json")
    @PUT("/student/update/{id}")
    fun updateStudent ( @Path("id") id: String?, @Body student: Student, @Header ("Authorization")  token: String?): Call<SingUpBody>

}