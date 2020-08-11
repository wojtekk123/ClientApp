package pl.e2d.clientapp.retrofit

import android.content.Context
import android.widget.Toast
import pl.e2d.clientapp.api.ReservationInterface
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback

interface CallbackAllReservation{
    fun onSuccess(bodyList:MutableList<ReservationDto>)
}


class ReservationRequestRetrofit {

    private val BASE_URL: String = "http://192.168.1.150:8082"

    companion object  {
        private var flag:Boolean = false
    }


    fun getAllReservation (callbackAllReservation:CallbackAllReservation, context: Context) {

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()

        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ReservationInterface::class.java)
            val call: Call<List<ReservationDto>> = request.getAllReservation("Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<List<ReservationDto>> {
                override fun onFailure(call: Call<List<ReservationDto>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<ReservationDto>>, response: retrofit2.Response<List<ReservationDto>>
                ) {
                    if (response.code() == 200) {
                        callbackAllReservation.onSuccess(response.body() as MutableList<ReservationDto>)
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
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

    fun deleteReservation (context: Context, id:Long? ):Boolean {
        flag = false

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()
        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ReservationInterface::class.java)
            val call = request.deleteReservation(id,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<ReservationDto> {
                override fun onFailure(call: Call<ReservationDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ReservationDto>, response: retrofit2.Response<ReservationDto>) {
                    if (response.code() == 200) {
                        flag = true
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
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
        return StudentRequestRetrofit.flag
    }


    fun approveReservation ( context: Context, id:Long? ) : Boolean {
        flag = false

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()
        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ReservationInterface::class.java)
            val call = request.approveReservation(id,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<ReservationDto> {
                override fun onFailure(call: Call<ReservationDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ReservationDto>, response: retrofit2.Response<ReservationDto>) {
                    if (response.code() == 200) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                        flag = true
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
        return flag
    }

    fun declineReservation ( context: Context, id:Long? ) : Boolean {
        flag = false

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()
        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ReservationInterface::class.java)
            val call = request.declineReservation(id,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<ReservationDto> {
                override fun onFailure(call: Call<ReservationDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ReservationDto>, response: retrofit2.Response<ReservationDto>) {
                    if (response.code() == 200) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                        flag = true
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
        return flag
    }

}

