package pl.e2d.clientapp.api

import pl.e2d.clientapp.model.Response
import pl.e2d.clientapp.model.SignInBody
import pl.e2d.clientapp.model.SingUpBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("/signin")
    fun signIn (@Body loginData:SignInBody): Call<Response>


    @Headers("Content-Type:application/json")
    @POST("/signup")
    fun signUp (@Body registrationData: SingUpBody, @Header ("Authorization")  token: String?): Call<SingUpBody>


}
