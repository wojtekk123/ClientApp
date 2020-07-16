package pl.e2d.clientapp.retrofit

import android.content.Context
import android.widget.Toast
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.dto.SingUpBodyDto
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback

class StudentRequestRetrofit {

    companion object {
        var flag:Boolean = false
    }
    private val BASE_URL: String = "http://192.168.1.150:8080"

    fun updateStudent(context: Context, studentDto: StudentDto) :Boolean {

        val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
        request.updateStudent(studentDto.id.toString(), studentDto,"Bearer "+ TokenAccess.getMyStringData()).enqueue(object : Callback<SingUpBodyDto> {
            override fun onFailure(call: Call<SingUpBodyDto>, t: Throwable) {
                flag = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<SingUpBodyDto>, response: retrofit2.Response<SingUpBodyDto>) {
                if (response.code() == 200) {
                    flag = true
                    Toast.makeText(context,"Registered success!", Toast.LENGTH_SHORT).show()
                } else {
                    flag = false
                    Toast.makeText(context,"Access Denied!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return flag
    }
}