package pl.e2d.clientapp.retrofit

import android.content.Context
import android.content.Intent
import android.widget.Toast
import pl.e2d.clientapp.activities.PanelActivity
import pl.e2d.clientapp.api.ApiInterface
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.model.Response
import pl.e2d.clientapp.model.SingUpBody
import pl.e2d.clientapp.model.masterDataEntity.Student
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback

class StudentRequestRetrofit {

    private val BASE_URL: String = "http://192.168.1.150:8080"


    fun updateStudent(context: Context, student: Student) :Boolean {
        var flag = false

        val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
        request.updateStudent(student.id.toString(), student,"Bearer "+ TokenAccess.getMyStringData()).enqueue(object : Callback<SingUpBody> {
            override fun onFailure(call: Call<SingUpBody>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                flag = false
            }
            override fun onResponse(call: Call<SingUpBody>, response: retrofit2.Response<SingUpBody>) {
                if (response.code() == 200) {
                    Toast.makeText(context,"Registered success!", Toast.LENGTH_SHORT).show()
                    flag = true
                } else {
                    Toast.makeText(context,"Access Denied!", Toast.LENGTH_SHORT).show()
                    flag = false
                }
            }
        })
        return flag
    }
}