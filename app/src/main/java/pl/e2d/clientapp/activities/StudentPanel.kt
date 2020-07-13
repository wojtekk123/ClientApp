package pl.e2d.clientapp.activities

import android.content.DialogInterface
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.basic_activity.*
import kotlinx.android.synthetic.main.popup_window.*
import kotlinx.android.synthetic.main.studnet_panel.*
import org.w3c.dom.Text
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.ListAdapter
import pl.e2d.clientapp.adapter.ListAdapterPopUp
import pl.e2d.clientapp.api.ApiInterface
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.model.masterDataEntity.Student
import pl.e2d.clientapp.parser.ParserMaster
import pl.e2d.clientapp.retrofit.StudentRequestRetrofit
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback
import java.lang.IllegalArgumentException


class StudentPanel : AppCompatActivity() {

    private val BASE_URL: String = "http://192.168.1.150:8080"
    private val TAG = "StudentPanel"
    private var listOfStudents:ArrayList<Student> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.studnet_panel)

        button2.setOnClickListener{
            val popupWindow = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.popup_window, null)
            popupWindow.contentView = view
            val changeButton = view.findViewById<Button>(R.id.pp_change)

            changeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            popupWindow.showAsDropDown(button2)

        }

        getAllButton.setOnClickListener {

            if (TokenAccess.getMyStringData().equals(null)) {
                Toast.makeText(this@StudentPanel, "Lack of token!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val request = ServiceBuilder.getRetrofitInstance(BASE_URL).create(StudentInterface::class.java)
                val call = request.getAllStudent("Bearer " + TokenAccess.getMyStringData())

                call.enqueue(object : Callback<List<Student>> {
                    override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                        override fun onResponse(call: Call<List<Student>>, response: retrofit2.Response<List<Student>>) {

                            if (response.code() == 200) {
                                Toast.makeText(this@StudentPanel,"Login success!",Toast.LENGTH_SHORT).show()

                                val json: String = Gson().toJson(response.body())
                                listOfStudents = ParserMaster().jsonStudentResult(json)
                                val adapter = ListAdapter(this@StudentPanel, listOfStudents)
                                listView.adapter = adapter

                            } else {
                                Toast.makeText(this@StudentPanel,"Access denied", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, views, position, id ->

                val popupWindow = PopupWindow(this)
                val view = layoutInflater.inflate(R.layout.popup_window, null)
                val context = view.context
                val button = view.findViewById<Button>(R.id.pp_change)
                val listView = view.findViewById<ListView>(R.id.popup_listView)
                val student = listOfStudents[position]


                popupWindow.contentView = view

                val studentPopUp = ParserMaster().jsonStudentPopUp(listOfStudents[position])
                val adapterPopUp = ListAdapterPopUp(context, studentPopUp)
                listView.adapter = adapterPopUp

                button.setOnClickListener {
                    popupWindow.dismiss()
                }

                popupWindow.showAsDropDown(getAllButton)

                listView.setOnItemClickListener { parent1, view1, position1, id1 ->

                    val itemListView = view1.findViewById(R.id.textView_popUp) as TextView
                    val dialog = AlertDialog.Builder(this).create()
                    val editText = EditText(this)

                    dialog.setView(editText)
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                        "SAVE",
                        DialogInterface.OnClickListener { dialog1, which ->
                            setStudentValue(student, editText.text.toString(), position1)
                            if(StudentRequestRetrofit().updateStudent(this@StudentPanel, student)){
                                itemListView.text = editText.text
                                listOfStudents[position] = student
                            }
                        })

                    editText.setText(itemListView.text)
                    dialog.show()

                }
            }
        }

    private fun setStudentValue (student:Student, value:String, position:Int) {

        when (position){
            0->student.user.firstName = value
            1->student.user.secondName = value
            2->student.user.email =  value
            3->student.user.phoneNumber = value
            4->student.schoolId = value.toLong()
            5->student.startEducation = value
            6->student.endEducation = value
            else -> throw IllegalArgumentException()
        }
    }
}




