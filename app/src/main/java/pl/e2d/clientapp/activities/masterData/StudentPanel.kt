package pl.e2d.clientapp.activities.masterData

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.studnet_panel.*
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.AdapterStudentPopUpAdd
import pl.e2d.clientapp.adapter.AdapterStudentPopUpList
import pl.e2d.clientapp.adapter.AdapterStudentList
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.mapper.mapToDto
import pl.e2d.clientapp.mapper.mapToModel
import pl.e2d.clientapp.model.Student
import pl.e2d.clientapp.parser.ParserMaster
import pl.e2d.clientapp.retrofit.CallBackAllStudent
import pl.e2d.clientapp.retrofit.StudentRequestRetrofit
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

import java.util.stream.Collectors
import kotlin.collections.ArrayList


class StudentPanel : AppCompatActivity() {

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
            val popupListViewAdd = viewListAdd.findViewById(R.id.popup_listView_addStudent) as ListView
            val confirmButton = viewListAdd.findViewById(R.id.pp_confirm_Student) as Button
            val closeButton = viewListAdd.findViewById(R.id.pp_closeStudent) as Button
            val studentVariable: List<String> = resources.getStringArray(R.array.list_student_variable).toList()
            val adapterPopupAdd = AdapterStudentPopUpAdd(context, studentVariable)

            popupWindow.contentView = viewListAdd
            popupListViewAdd.adapter = adapterPopupAdd
            popupWindow.showAsDropDown(addStudentButton_panel)
            popupWindow.setFocusable(true)
            popupWindow.update()

            popupListViewAdd.setOnItemClickListener { parent, view, position, id ->

                val builder = AlertDialog.Builder(this)
                val itemListView = view.findViewById(R.id.textView_adapter_list) as TextView
                val editText = EditText(this)
                builder.setTitle("Edit field")
                builder.setView(editText)
                builder.setPositiveButton("SAVE",  DialogInterface.OnClickListener { dialog1, which ->

                    try {
                        setStudentValue(newStudent, editText.text.toString(), position)
                        itemListView.text = editText.text
                    }catch (e:IllegalArgumentException ){
                        AlertDialog.Builder(this@StudentPanel)
                            .setTitle("Error")
                            .setMessage(e.message)
                            .show()
                    }
                })

                val dialog: AlertDialog = builder.create()
                dialog.show()

                if(itemListView.text.toString().isEmpty()){
                    itemListView.error
                }
            }

            closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            confirmButton.setOnClickListener {
                StudentRequestRetrofit().addStudent(this@StudentPanel, mapToDto(newStudent))

            }
        }

        allStudentButton.setOnClickListener {

             StudentRequestRetrofit().getAllStudent(object :CallBackAllStudent {
                override fun onSuccess(bodyList: MutableList<StudentDto>) {
                    val json: String = Gson().toJson(bodyList)
                    listOfStudents =ParserMaster().jsonStudentResult(json).stream().map { e -> mapToModel(e) }.collect(Collectors.toList())
                    val adapter = AdapterStudentList(this@StudentPanel,listOfStudents)
                    listView_studentPanel.adapter = adapter
                }
            },this@StudentPanel)

            listView_studentPanel.setOnItemClickListener { parent, views, position, id ->

                val popupWindow = PopupWindow(this)
                val view = layoutInflater.inflate(R.layout.popup_student_list_view, null)
                val context = view.context
                val changeButton = view.findViewById<Button>(R.id.pp_change)
                val deleteButton = view.findViewById<Button>(R.id.pp_delete)
                val listViewStudentList = view.findViewById<ListView>(R.id.popup_listView_listStudent)
                var student = listOfStudents[position]
                val copyStudent = student.copy()
                val studentPopUp = ParserMaster().parsToDisplayData(listOfStudents[position])
                val adapterPopUp = AdapterStudentPopUpList(context, studentPopUp)

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

                            try {
                                setStudentValue(copyStudent, editText.text.toString(), position1)
                                if (StudentRequestRetrofit().updateStudent(this@StudentPanel, mapToDto(student))) {
                                    itemListView.text = editText.text
                                    student = copyStudent
                                    listOfStudents[position] = student
                                }
                            } catch (e: IllegalArgumentException) {
                                AlertDialog.Builder(this@StudentPanel)
                                    .setTitle("Error")
                                    .setMessage(e.message)
                                    .show()
                            }
                        })

                    editText.setText(itemListView.text)
                    dialog.show()
                }
                deleteButton.setOnClickListener {

                    val builder = AlertDialog.Builder(this@StudentPanel)
                    builder.setTitle("Are you sure you want to delete this user?")
                    builder.setPositiveButton("YES"){dialog, which ->

                     if(StudentRequestRetrofit().deleteStudent(this@StudentPanel, listOfStudents[position].id)){
                        listOfStudents.removeAt(position)
                        val adapter = AdapterStudentList(this@StudentPanel,
                            listOfStudents
                        )
                        listView_studentPanel.adapter = adapter
                        popupWindow.dismiss()
                     }
                    }
                    builder.setNegativeButton("No"){dialog,which ->}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
                changeButton.setOnClickListener {
                    popupWindow.dismiss()
                }
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun setStudentValue (student:Student, value:String, position:Int){


        val emailPattern:Pattern = Patterns.EMAIL_ADDRESS
        val phonePattern:Pattern = Patterns.PHONE
        val nameAndSurnamePattern:Pattern = "^[a-zA-Z]+".toRegex().toPattern()
        val schoolNumberPattern:Pattern = "^[12]".toRegex().toPattern()
        val dateParent:Pattern = "^([0]?[1-9]|[1|2][0-9]|[3][0|1])[-]([0]?[1-9]|[1][0-2])[-]([0-9]{4}|[0-9]{2})\$".toRegex().toPattern()

        when (position) {
            0 -> if (nameAndSurnamePattern.matcher(value).matches()) {
                student.user?.firstName = value
            } else throw IllegalArgumentException("Wrong format name")
            1 ->  if (nameAndSurnamePattern.matcher(value).matches()) {
                student.user?.secondName = value
            } else throw IllegalArgumentException("Wrong format name")
            2 -> if (emailPattern.matcher(value).matches()) {
                student.user?.email = value
            } else throw IllegalArgumentException("Wrong email address format")
            3 -> if (phonePattern.matcher(value).matches()) {
                student.user?.phoneNumber = value
            } else throw IllegalArgumentException("Wrong number format")
            4 -> if (schoolNumberPattern.matcher(value).matches()) {
                student.schoolId = value.toLong()
            } else throw IllegalArgumentException("Wrong number format")
            5 -> if (dateParent.matcher(value).matches()) {
                student.startEducation = setStudentDate(value)
            } else throw IllegalArgumentException("Wrong date format. Try dd-mm-yyyy ")
            6 -> if (dateParent.matcher(value).matches()) {
                student.endEducation = setStudentDate(value)
            } else throw IllegalArgumentException("Wrong date format. Try dd-mm-yyyy")
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
