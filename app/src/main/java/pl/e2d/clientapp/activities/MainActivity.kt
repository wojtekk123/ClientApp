package pl.e2d.clientapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.et_password
import kotlinx.android.synthetic.main.activity_main.et_user_name
import pl.e2d.clientapp.R
import pl.e2d.clientapp.api.ApiInterface
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import pl.e2d.clientapp.dto.ResponseDto
import pl.e2d.clientapp.dto.SignInBodyDto
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

    private val BASE_URL:String = "http://192.168.1.150:8081"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSubmit = findViewById<Button>(R.id.btn_submit)
        btn_sign_up.setOnClickListener {
            startActivity(Intent(this@MainActivity,RegistrationActivity::class.java))
            finish()
        }

        buttonSubmit.setOnClickListener{


            val login = et_user_name.text.toString().trim()
            val password = et_password.text.toString().trim()

            if(login.isEmpty()){
                et_user_name.error = "Email required"
                et_user_name.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                et_password.error = "Password required"
                return@setOnClickListener
            }


            val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ApiInterface::class.java)
             request.signIn(SignInBodyDto(login,password)).enqueue(object: Callback<ResponseDto>{
                    override fun onFailure(call: Call<ResponseDto>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ResponseDto>, responseDto: retrofit2.Response<ResponseDto>) {
                        if (responseDto.code() == 200) {
                            Toast.makeText(this@MainActivity, "Login success!", Toast.LENGTH_SHORT).show()
                            TokenAccess.token = responseDto.body()?.token
                            startActivity(Intent(this@MainActivity,PanelActivity::class.java))
                            finish()

                        } else {
                            Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
             })

        }
    }
}