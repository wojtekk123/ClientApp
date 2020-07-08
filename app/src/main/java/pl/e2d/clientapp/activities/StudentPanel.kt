package pl.e2d.clientapp.activities

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.studnet_panel.*
import org.json.JSONArray
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.ListAdapter
import pl.e2d.clientapp.api.ApiInterface
import pl.e2d.clientapp.model.masterDataEntity.Student
import pl.e2d.clientapp.model.masterDataEntity.User
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback


class StudentPanel : AppCompatActivity() {

    private val BASE_URL: String = "http://192.168.1.150:8080"
    private val TAG = "StudentPanel"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.studnet_panel)

        val findViewById = findViewById<ListView>(R.id.listView)
        val students = mutableListOf<Student>()

        getAllButton.setOnClickListener {


            if (TokenAccess.getMyStringData().equals(null)) {
                Toast.makeText(this@StudentPanel, "Lack of token!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ApiInterface::class.java)
                val call = request.getAllStudent("Bearer " + TokenAccess.getMyStringData())

                call.enqueue(object : Callback<List<Student>> {
                    override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                        override fun onResponse(call: Call<List<Student>>, response: retrofit2.Response<List<Student>>) {

                            if (response.code() == 200) {
                                Toast.makeText(this@StudentPanel,"Login success!",Toast.LENGTH_SHORT).show()

                                 val json:String = Gson().toJson(response.body())
                                println("=+=+="+ json)
                                jsonResult(json)

                            } else {
                                Toast.makeText(this@StudentPanel,"Access denied", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }
    }
    private fun jsonResult(jsonString: String?)  {
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<Student>()
        var i = 0
        while (i < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                Student(
                    jsonObject.getLong("id"),
                    User(
                        jsonObject.getJSONObject("user").getLong("id"),
                        jsonObject.getJSONObject("user").getString("firstName"),
                        jsonObject.getJSONObject("user").getString("secondName"),
                        jsonObject.getJSONObject("user").getString("email"),
                        jsonObject.getJSONObject("user").getString("phoneNumber")),
                    jsonObject.getLong("schoolId"),
                    jsonObject.getString("startEducation"),
                    jsonObject.getString("endEducation")
                )
            )
            i++
        }
        val adapter = ListAdapter(this@StudentPanel, list)
        listView.adapter= adapter
    }
}


