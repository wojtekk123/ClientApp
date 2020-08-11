package pl.e2d.clientapp.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import pl.e2d.clientapp.R
import pl.e2d.clientapp.dto.masterDataEntity.UserByIdDto
import pl.e2d.clientapp.model.Reservation
import pl.e2d.clientapp.retrofit.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class AdapterReservation(private val reservationList: MutableList<Reservation>) : RecyclerView.Adapter<AdapterReservation.ExampleViewHolder>() {

    companion object{
        private var tempContext:Context? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_reservation_list, parent, false)
        tempContext = itemView.context
        return ExampleViewHolder(itemView)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val calendar: Calendar = Calendar.getInstance()
        val currentItem = reservationList[position]
        calendar.time =
            Date.from(currentItem.rideDateForm.atZone(ZoneId.systemDefault())!!.toInstant())
        val rideDateForm: String =
            (calendar as GregorianCalendar).toZonedDateTime().toLocalDateTime().format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            ).toString()
        calendar.time =
            Date.from(currentItem.rideDateTo.atZone(ZoneId.systemDefault())!!.toInstant())
        val rideDateTo: String = calendar.toZonedDateTime().toLocalDateTime().format(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        ).toString()

        holder.id.text = currentItem.id.toString()
        holder.carId.text = currentItem.carId.toString()
        holder.dateFrom.text = rideDateForm
        holder.dateTo.text = rideDateTo

        StudentRequestRetrofit().getStudentById(
            currentItem.studentId,
            tempContext,
            object : CallBackStudent {
                override fun onSuccess(body: UserByIdDto?) {
                    if (body != null) {
                        holder.studentName.text = body.username!!.split(" ")[0]
                        holder.studentSurname.text = body.username.split(" ")[1]
                    }
                }
            })

        InstructorRequestRetrofit().getInstructorById(
            currentItem.instructorId,
            tempContext,
            object : CallBackInstructorById {
                override fun onSuccess(body: UserByIdDto?) {
                    holder.instructorName.text = body?.username!!.split(" ")[0]
                    holder.instructorSurname.text = body.username.split(" ")[1]
                }

            })

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(tempContext!!)
            builder.setTitle("Are you sure you want to delete this reservation?")
            builder.setPositiveButton("YES") { dialog, which ->
                if (ReservationRequestRetrofit().deleteReservation(tempContext!!, reservationList[position].id)) {
                    reservationList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
            builder.setNegativeButton("No") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        holder.approveButton.setOnClickListener {
            val builder = AlertDialog.Builder(tempContext!!)
            builder.setTitle("Are you sure you want to approve this reservation?")
            builder.setPositiveButton("YES") { dialog, which ->
                if (ReservationRequestRetrofit().approveReservation(tempContext!!, reservationList[position].id)) {
                    reservationList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
            builder.setNegativeButton("No") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }




    override fun getItemCount() = reservationList.size

     inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = itemView.findViewById<TextView>(R.id.textView_reservation_id)
        val studentName = itemView.findViewById<TextView>(R.id.textView_reservation_student_name)
        val studentSurname = itemView.findViewById<TextView>(R.id.textView_reservation_student_surname)
        val instructorName = itemView.findViewById<TextView>(R.id.textView_reservation_instructor_name)
        val instructorSurname = itemView.findViewById<TextView>(R.id.textView_reservation_instructor_surname)
        val carId = itemView.findViewById<TextView>(R.id.textView_textView_reservation_car_id)
        var dateFrom = itemView.findViewById<TextView>(R.id.textView_reservation_date_form)
        val dateTo = itemView.findViewById<TextView>(R.id.textView_reservation_date_to)
        val deleteButton = itemView.findViewById<Button>(R.id.reservation_delete_button)
        val approveButton = itemView.findViewById<Button>(R.id.reservation_approve_button)
    }
}
