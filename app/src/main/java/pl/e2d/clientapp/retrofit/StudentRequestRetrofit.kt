package pl.e2d.clientapp.retrofit

import android.content.Context
import android.widget.Toast
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback

interface CallBackAllStudent{
    fun onSuccess(bodyList:MutableList<StudentDto>)
}
interface CallBackStudent{
    fun onSuccess(body:StudentDto?)
}

class StudentRequestRetrofit {

    private val BASE_URL: String = "http://192.168.1.150:8080"

    companion object {
        var flag:Boolean = false
    }

    fun updateStudent(context: Context, studentDto: StudentDto) :Boolean {

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()

        } else {
            val request =ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
            val call = request.updateStudent(studentDto.id, studentDto,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<StudentDto> {
                override fun onFailure(call: Call<StudentDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<StudentDto>, response: retrofit2.Response<StudentDto>) {
                    if (response.code() == 200) {
                        flag = true
                        Toast.makeText(context, "Registered success!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(context, "Access Denied!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(context, "Resource doesn't exist!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
        return flag
    }

    fun getAllStudent (callback: CallBackAllStudent, context: Context) {

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()

        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
            val call = request.getAllStudent("Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<List<StudentDto>> {
                override fun onFailure(call: Call<List<StudentDto>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<List<StudentDto>>,response: retrofit2.Response<List<StudentDto>>
                ) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.body() as MutableList<StudentDto>)
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

    fun deleteStudent (context: Context, id:Long? ):Boolean {
        flag = false

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()
        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
            val call = request.deletedStudent(id,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<StudentDto> {
                override fun onFailure(call: Call<StudentDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<StudentDto>,response: retrofit2.Response<StudentDto>) {
                    if (response.code() == 200) {
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
        return flag
    }

    fun addStudent(context: Context, studentDto: StudentDto) :Boolean {
        flag = false

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()

        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
            val call = request.addStudent(studentDto, "Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<StudentDto> {
                override fun onFailure(call: Call<StudentDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<StudentDto>, response: retrofit2.Response<StudentDto> ) {
                    if (response.code() == 200) {
                        flag = true
                        Toast.makeText(context, "Registered success!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 403) {
                        Toast.makeText(context, "Access Denied!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 404) {
                        Toast.makeText(context, "Resource doesn't exist!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
        return flag
    }

    fun getStudent (id: Long, context: Context, callback: CallBackStudent) {

        if (TokenAccess.getMyStringData().equals(null)) {
            Toast.makeText(context, "Lack of token!", Toast.LENGTH_SHORT).show()

        } else {
            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
            val call = request.getStudent(id,"Bearer " + TokenAccess.getMyStringData())

            call.enqueue(object : Callback<StudentDto> {
                override fun onFailure(call: Call<StudentDto>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<StudentDto>,response: retrofit2.Response<StudentDto>
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