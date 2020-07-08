package pl.e2d.clientapp.activities

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.user_registration.*
import kotlinx.android.synthetic.main.user_registration.et_password
import kotlinx.android.synthetic.main.user_registration.et_user_name
import org.json.JSONArray
import pl.e2d.clientapp.R
import pl.e2d.clientapp.api.ApiInterface
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import pl.e2d.clientapp.model.SingUpBody
import retrofit2.Call
import retrofit2.Callback
import java.net.HttpURLConnection
import java.net.URL


class RegistrationActivity : AppCompatActivity() {

    private var roles = arrayOf("Admin", "Student", "Instructor", "School")
    private  val BASE_URL:String = "http://192.168.1.150:8081"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            roles
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                text_view.text = "Role : ${parent?.getItemAtPosition(position).toString()}"
            }


        }

        register.setOnClickListener {

            val login = et_user_name.text.toString().trim()
            val password = et_password.text.toString().trim()
            val role = spinner.selectedItem.toString().trim()

            if (login.isEmpty()) {
                et_user_name.error = "Email required"
                et_user_name.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                et_password.error = "Password required"
                return@setOnClickListener
            }

            if (role.isEmpty()) {
                et_password.error = "Role required"
                return@setOnClickListener
            }

            if (TokenAccess.getMyStringData().equals(null)) {
                Toast.makeText(this@RegistrationActivity, "Access denied!", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(ApiInterface::class.java)
                request.signUp(SingUpBody(login, password, role), "Bearer "+ TokenAccess.getMyStringData())
                    .enqueue(object : Callback<SingUpBody> {
                        override fun onFailure(call: Call<SingUpBody>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<SingUpBody>, response: retrofit2.Response<SingUpBody>) {
                            if (response.code() == 200) {
                                Toast.makeText(this@RegistrationActivity,"Registered success!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@RegistrationActivity,"Registered failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

            }
        }
    }


}
