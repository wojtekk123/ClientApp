package pl.e2d.clientapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.et_password
import kotlinx.android.synthetic.main.activity_main.et_user_name
import pl.e2d.clientapp.R
import pl.e2d.clientapp.api.ApiInterface
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import pl.e2d.clientapp.model.Response
import pl.e2d.clientapp.model.SignInBody
import retrofit2.Call
import retrofit2.Callback



class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sign_up.setOnClickListener {
            startActivity(Intent(this@MainActivity,RegistrationActivity::class.java))
        }

        btn_submit.setOnClickListener{

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


            val request = ServiceBuilder.getRetrofitInstance().create(ApiInterface::class.java)
             request.signIn(SignInBody(login,password)).enqueue(object: Callback<Response>{
                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Response>,response: retrofit2.Response<Response>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@MainActivity, "Login success!", Toast.LENGTH_SHORT).show()
                            TokenAccess.token = response.body()?.token
                            startActivity(Intent(this@MainActivity,PanelActivity::class.java))

                        } else {
                            Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
             })
        }
    }
}