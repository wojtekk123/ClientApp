package pl.e2d.clientapp.api

import pl.e2d.clientapp.dto.ResponseDto
import pl.e2d.clientapp.dto.SignInBodyDto
import pl.e2d.clientapp.dto.SingUpBodyDto
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("/signin")
    fun signIn (@Body loginData:SignInBodyDto): Call<ResponseDto>


    @Headers("Content-Type:application/json")
    @POST("/signup")
    fun signUp (@Body registrationData: SingUpBodyDto, @Header ("Authorization")  token: String?): Call<SingUpBodyDto>


}
