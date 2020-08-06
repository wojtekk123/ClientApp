package pl.e2d.clientapp.retrofit

import android.content.Context
import android.widget.Toast
import pl.e2d.clientapp.api.InstructorInterface
import pl.e2d.clientapp.dto.masterDataEntity.UserByIdDto
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback

interface CallBackInstructorById{
    fun onSuccess(body:UserByIdDto?)
}

class InstructorRequestRetrofit {

    private val BASE_URL: String = "http://192.168.1.150:8080"


    fun getInstructorById (id: Long, context: Context?, callback: CallBackInstructorById) {

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()

        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(InstructorInterface::class.java)
            val call = request.getInstructorById(id,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<UserByIdDto> {
                override fun onFailure(call: Call<UserByIdDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<UserByIdDto>, response: retrofit2.Response<UserByIdDto>
                ) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.body())
                        Toast.makeText(context, "Login success!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(context, "Access Denied!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(context, "Resource doesn't exist!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}