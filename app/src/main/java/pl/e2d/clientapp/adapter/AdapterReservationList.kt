package pl.e2d.clientapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import pl.e2d.clientapp.R
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.masterDataEntity.UserByIdDto
import pl.e2d.clientapp.model.Reservation
import pl.e2d.clientapp.retrofit.CallBackInstructorById
import pl.e2d.clientapp.retrofit.CallBackStudent
import pl.e2d.clientapp.retrofit.InstructorRequestRetrofit
import pl.e2d.clientapp.retrofit.StudentRequestRetrofit
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AdapterReservationList(val context: Context, val list: List<Reservation>) : BaseAdapter() {

    companion object{
        var student = StudentDto()

    }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("ViewHolder")
        override fun getView(position: Int, currentView: View?, parent: ViewGroup?): View {

            var tempList: MutableList<Reservation> = list as MutableList<Reservation>

            val calendar: Calendar = Calendar.getInstance()
            val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_reservation_list, parent, false)
            val id = view.findViewById<TextView>(R.id.textView_reservation_id)
            val studentName = view.findViewById<TextView>(R.id.textView_reservation_student_name)
            val studentSurname = view.findViewById<TextView>(R.id.textView_reservation_student_surname)
            val instructorName = view.findViewById<TextView>(R.id.textView_reservation_instructor_name)
            val instructorSurname = view.findViewById<TextView>(R.id.textView_reservation_instructor_surname)
            val carId = view.findViewById<TextView>(R.id.textView_textView_reservation_car_id)
            val dateFrom = view.findViewById<TextView>(R.id.textView_reservation_date_form)
            val dateTo = view.findViewById<TextView>(R.id.textView_reservation_date_to)
            val inflater:LayoutInflater =  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val reservationContext = inflater.inflate(R.layout.reservation_panel, parent, false).context
            calendar.time = Date.from(tempList[position].rideDateForm.atZone(ZoneId.systemDefault())!!.toInstant())
            val rideDateForm:String = (calendar as GregorianCalendar).toZonedDateTime().toLocalDateTime().format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)).toString()
            calendar.time = Date.from(tempList[position].rideDateTo.atZone(ZoneId.systemDefault())!!.toInstant())
            val rideDateTo:String = calendar.toZonedDateTime().toLocalDateTime().format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)).toString()


            StudentRequestRetrofit().getStudentById(tempList[position].studentId, reservationContext,object :CallBackStudent {
                override fun onSuccess(body: UserByIdDto?) {
                    studentName.text =  body?.username!!.split(" ")[0]
                    studentSurname.text = body.username.split(" ")[1]
                }
            } )

            InstructorRequestRetrofit().getInstructorById(tempList[position].instructorId, reservationContext, object :CallBackInstructorById {
                override fun onSuccess(body: UserByIdDto?) {
                    instructorName.text = body?.username!!.split(" ")[0]
                    instructorSurname.text = body.username.split(" ")[1]
                }
            } )

            id.text = position.toString()
            carId.text = tempList[position].carId.toString()
            dateFrom.text = rideDateForm
            dateTo.text = rideDateTo

            val deleteButton: Button = view.findViewById(R.id.reservation_delete_button)

            deleteButton.isClickable = true


            deleteButton.setOnClickListener(object : View.OnClickListener {

                override fun onClick(v: View?) {
                    tempList.removeAt(position)
                    Toast.makeText(context, "Access Denied!", Toast.LENGTH_SHORT).show()

                }

            })



            return view
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }
    }

