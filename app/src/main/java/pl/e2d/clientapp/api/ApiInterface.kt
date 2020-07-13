package pl.e2d.clientapp.api

import pl.e2d.clientapp.model.Response
import pl.e2d.clientapp.model.SignInBody
import pl.e2d.clientapp.model.SingUpBody
import pl.e2d.clientapp.model.masterDataEntity.Student
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("/signin")
    fun signIn (@Body loginData:SignInBody): Call<Response>


    @Headers("Content-Type:application/json")
    @POST("/signup")
    fun signUp (@Body registrationData: SingUpBody, @Header ("Authorization")  token: String?): Call<SingUpBody>


}
