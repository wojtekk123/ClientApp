package pl.e2d.clientapp.retrofit

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.studnet_panel.*
import pl.e2d.clientapp.activities.StudentPanel
import pl.e2d.clientapp.adapter.ListAdapter
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.dto.SingUpBodyDto
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.mapper.mapToModel
import pl.e2d.clientapp.model.Student
import pl.e2d.clientapp.parser.ParserMaster
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors

class StudentRequestRetrofit {

    companion object {
        var flag:Boolean = false
        var responseBody:List<StudentDto>? = null

    }
    private val BASE_URL: String = "http://192.168.1.150:8080"

    fun updateStudent(context: Context, studentDto: StudentDto) :Boolean {

        val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
        val call =  request.updateStudent(studentDto.id.toString(), studentDto,"Bearer "+ TokenAccess.getMyStringData())
            call.enqueue(object : Callback<StudentDto> {
            override fun onFailure(call: Call<StudentDto>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<StudentDto>, response: retrofit2.Response<StudentDto>) {
                if (response.code() == 200) {
                    flag = true
                    Toast.makeText(context,"Registered success!", Toast.LENGTH_SHORT).show()
                } else if (response.code() == 403) {
                    Toast.makeText(context,"Access Denied!", Toast.LENGTH_SHORT).show()
                }else if (response.code() == 404) {
                    Toast.makeText(context, "Resource doesn't exist!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return flag
    }

    fun getAllStudent (context: Context): List<StudentDto>? {

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
                        Toast.makeText(context, "Login success!", Toast.LENGTH_SHORT).show()
                        responseBody = response.body()

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
        return responseBody
    }
}