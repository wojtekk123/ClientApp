package pl.e2d.clientapp.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import pl.e2d.clientapp.dto.scheduler.ReservationDto
import pl.e2d.clientapp.enums.ReservationType
import pl.e2d.clientapp.model.Reservation
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun mapToModel(reservation: ReservationDto): Reservation {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSX", Locale.GERMAN)

    return Reservation(
        reservation.id,
        reservation.instructorId,
        reservation.studnetId,
        reservation.carId,
        LocalDateTime.parse(reservation.rideDateForm,formatter),
        LocalDateTime.parse(reservation.rideDateTo,formatter),
        when (reservation.typeReservation){
            "OPEN"-> ReservationType.OPEN
            "APPROVE"-> ReservationType.APPROVE
            "DECLINE"-> ReservationType.DECLINE
            else -> throw IllegalArgumentException()
        }
    )
}