package pl.e2d.clientapp.activities.timeScheduler

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_tab_approve.*
import kotlinx.android.synthetic.main.fragment_tab_open.*
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.AdapterReservation
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import pl.e2d.clientapp.enums.ReservationType
import pl.e2d.clientapp.mapper.mapToModel
import pl.e2d.clientapp.model.Reservation
import pl.e2d.clientapp.parser.ParserScheduler
import pl.e2d.clientapp.retrofit.CallbackAllReservation
import pl.e2d.clientapp.retrofit.ReservationRequestRetrofit
import java.util.stream.Collectors

class ReservationFragmentOpen : Fragment() {

    companion object {
        private var listOfOpenReservation: MutableList<Reservation> = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_tab_open, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        ReservationRequestRetrofit().getAllReservation(object : CallbackAllReservation {
            override fun onSuccess(bodyList: MutableList<ReservationDto>) {

                val json: String = Gson().toJson(bodyList)
                listOfOpenReservation = ParserScheduler().jsonReservationResult(json).stream().map { e -> mapToModel(e) }.filter{d-> d.typeReservation == ReservationType.OPEN }.collect(Collectors.toList())
                val adapter = AdapterReservation(listOfOpenReservation)
                recycle_view_reservation_open.adapter = adapter
                recycle_view_reservation_open.layoutManager = LinearLayoutManager(view!!.context)
                recycle_view_reservation_open.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
            }
        }, requireView().context)
    }
}
