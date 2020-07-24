package pl.e2d.clientapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pl.e2d.clientapp.R
import pl.e2d.clientapp.dto.masterDataEntity.InstructorDto
import pl.e2d.clientapp.dto.masterDataEntity.StudentDto
import pl.e2d.clientapp.dto.masterDataEntity.UserByIdDto
import pl.e2d.clientapp.model.Reservation
import pl.e2d.clientapp.retrofit.*

class AdapterReservationList(val context: Context, val list: List<Reservation>) : BaseAdapter() {

    companion object{
        var student = StudentDto()

    }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, currentView: View?, parent: ViewGroup?): View {


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
            val temporaryContext = inflater.inflate(R.layout.reservation_panel, parent, false).context

            StudentRequestRetrofit().getStudent(list[position].studnet, temporaryContext,object :CallBackStudent {
                override fun onSuccess(body: StudentDto?) {
                    studentName.text = body?.userDto?.firstName
                    studentSurname.text = body?.userDto?.secondName
                }
            } )

            InstructorRequestRetrofit().getInstructorById(list[position].instructor, temporaryContext, object :CallBackInstructorById {
                override fun onSuccess(body: UserByIdDto?) {
                    instructorName.text = body?.username!!.split(" ")[0]
                    instructorSurname.text = body.username.split(" ")[1]
                }
            } )

            id.text = position.toString()
            carId.text = list[position].carId.toString()
            dateFrom.text = list[position].rideDateForm .toString()
            dateTo.text = list[position].rideDateTo.toString()
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