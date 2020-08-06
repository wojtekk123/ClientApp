package pl.e2d.clientapp.activities.timeScheduler

import android.os.Build
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.reservation_panel.*
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.AdapterReservation
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
                val adapter = AdapterReservation( listOfReservation)
                recycleListView_reservation.adapter = adapter
                recycleListView_reservation.layoutManager = LinearLayoutManager(this@ReservationPanel)
                recycleListView_reservation.setHasFixedSize(true)
                adapter.notifyDataSetChanged()


            }
        }, this@ReservationPanel)
    }
}