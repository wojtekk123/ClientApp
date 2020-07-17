package pl.e2d.clientapp.activities

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.studnet_panel.*
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.AdapterPopUpAdd
import pl.e2d.clientapp.adapter.AdapterPopUpList
import pl.e2d.clientapp.adapter.ListAdapter
import pl.e2d.clientapp.api.StudentInterface
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.mapper.mapToDto
import pl.e2d.clientapp.mapper.mapToModel
import pl.e2d.clientapp.model.Student
import pl.e2d.clientapp.parser.ParserMaster
import pl.e2d.clientapp.retrofit.StudentRequestRetrofit
import pl.e2d.clientapp.singletons.ServiceBuilder
import pl.e2d.clientapp.singletons.TokenAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class StudentPanel : AppCompatActivity() {

    private val BASE_URL: String = "http://192.168.1.150:8080"
    companion object {
        private var newStudent = Student()
        private var listOfStudents:MutableList<Student> = ArrayList()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.studnet_panel)

        val allStudentButton = findViewById<Button>(R.id.getAllButton_panel)
        val addStudentButton = findViewById<Button>(R.id.addStudentButton_panel)

        addStudentButton.setOnClickListener {

            val popupWindow = PopupWindow(this)
            val viewListAdd = layoutInflater.inflate(R.layout.popup_student_add, null)
            val context = viewListAdd.context
            val popupListAdd = viewListAdd.findViewById(R.id.popup_listView_addStudent) as ListView
            val confirmButton = viewListAdd.findViewById(R.id.pp_confirm_Student) as Button
            val closeButton = viewListAdd.findViewById(R.id.pp_closeStudent) as Button
            val studentVariable: List<String> = resources.getStringArray(R.array.list_student_variable).toList()
            val adapterPopupAdd = AdapterPopUpAdd(context, studentVariable)

            popupWindow.contentView = viewListAdd
            popupListAdd.adapter = adapterPopupAdd
            popupWindow.showAsDropDown(addStudentButton_panel)
            popupWindow.setFocusable(true)
            popupWindow.update()

            popupListAdd.setOnItemClickListener { parent, view, position, id ->

                val dialog = AlertDialog.Builder(this).create()
                val itemListView = view.findViewById(R.id.textView_adapter_list) as TextView
                val editText = EditText(this)
                dialogAcces(dialog, editText, position, itemListView)

                if(itemListView.text.toString().isEmpty()){
                    itemListView.error
                }

            }


            closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            confirmButton.setOnClickListener {

                print(newStudent)


            }
        }

        allStudentButton.setOnClickListener {

            val studentList: List<StudentDto>? = StudentRequestRetrofit().getAllStudent(this@StudentPanel)

            if (studentList!= null) {
                val json: String = Gson().toJson(studentList)
                listOfStudents =ParserMaster().jsonStudentResult(json).stream().map { e -> mapToModel(e) }.collect(Collectors.toList())
                val adapter = ListAdapter(this@StudentPanel, listOfStudents)
                listView_studentPanel.adapter = adapter
            }


            listView_studentPanel.setOnItemClickListener { parent, views, position, id ->

                val popupWindow = PopupWindow(this)
                val view = layoutInflater.inflate(R.layout.popup_student_list, null)
                val context = view.context
                val button = view.findViewById<Button>(R.id.pp_change)
                val listViewStudentList = view.findViewById<ListView>(R.id.popup_listView_listStudent)
                var student = listOfStudents[position]
                val copyStudent = student.copy()
                val studentPopUp = ParserMaster().jsonStudentPopUp(listOfStudents[position])
                val adapterPopUp = AdapterPopUpList(context, studentPopUp)

                popupWindow.contentView = view
                listViewStudentList.adapter = adapterPopUp
                popupWindow.showAsDropDown(getAllButton_panel)

                listViewStudentList.setOnItemClickListener { parent1, view1, position1, id1 ->

                    val itemListView = view1.findViewById(R.id.textView_adapter_list) as TextView
                    val dialog = AlertDialog.Builder(this).create()
                    val editText = EditText(this)

                    dialog.setTitle("Edit field")
                    dialog.setView(editText)
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                        "SAVE",
                        DialogInterface.OnClickListener { dialog1, which ->
                            setStudentValue(copyStudent, editText.text.toString(), position1)
                            if (StudentRequestRetrofit().updateStudent(this@StudentPanel, mapToDto(student))) {
                                itemListView.text = editText.text
                                student = copyStudent
                                listOfStudents[position] = student

                            }
                        })

                    editText.setText(itemListView.text)
                    dialog.show()

                }

                button.setOnClickListener {
                    popupWindow.dismiss()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dialogAcces(dialog: AlertDialog, editText: EditText, position: Int, itemListView: TextView) {
        dialog.setTitle("Edit field")
        dialog.setView(editText)
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,

            "SAVE",
            DialogInterface.OnClickListener { dialog1, which ->
                setStudentValue(newStudent, editText.text.toString(), position)
                itemListView.text = editText.text
            })

        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setStudentValue (student:Student, value:String, position:Int) {

        when (position){
            0->student.user?.firstName = value
            1->student.user?.secondName = value
            2->student.user?.email =  value
            3->student.user?.phoneNumber = value
            4->student.schoolId = value.toLong()
            5->student.startEducation =  setStudentDate(value)
            6->student.endEducation =  setStudentDate(value)
            else -> throw IllegalArgumentException()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setStudentDate (value:String): LocalDateTime {

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateLocalDateTime:LocalDateTime = LocalDateTime.ofInstant(sdf.parse(value).toInstant(), ZoneId.systemDefault())
        val formattedDate: String = dateLocalDateTime.format(formatter)
        return LocalDateTime.parse(formattedDate, formatter)

    }
}
