package pl.e2d.clientapp.activities.timeScheduler

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.reservation_panel.*
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.AdapterReservationList
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import pl.e2d.clientapp.mapper.mapToModel
import pl.e2d.clientapp.model.Reservation
import pl.e2d.clientapp.parser.ParserScheduler
import pl.e2d.clientapp.retrofit.CallbackAllReservation
import pl.e2d.clientapp.retrofit.ReservationRequestRetrofit
import java.util.stream.Collectors

class ReservationPanel : AppCompatActivity()  {

    companion object {
        private var listOfReservation: MutableList<Reservation> = ArrayList()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservation_panel)

        ReservationRequestRetrofit().getAllReservation(object : CallbackAllReservation {
            override fun onSuccess(bodyList: MutableList<ReservationDto>) {

                val json: String = Gson().toJson(bodyList)
                listOfReservation = ParserScheduler().jsonReservationResult(json).stream().map { e -> mapToModel(e) }.collect(Collectors.toList())
                val adapter = AdapterReservationList(this@ReservationPanel, listOfReservation)
                listView_reservation.adapter = adapter

            }
        }, this@ReservationPanel)


        listView_reservation.setOnItemClickListener( AdapterView.OnItemClickListener(){

        })
        }


    }
}

//
//val viewAdapterReservation =
//    layoutInflater.inflate(R.layout.adapter_reservation_list, null)
//val approveButton = viewAdapterReservation.findViewById<Button>(R.id.reservation_approve_button)
//approveButton.tag = views.tag
//approveButton.setOnClickListener {
//    Toast.makeText(views.context, "Success!", Toast.LENGTH_SHORT).show()