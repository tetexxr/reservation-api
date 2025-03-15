package com.reservation.api.infrastructure.controllers.reservations

import com.reservation.api.application.reservations.UpdateReservationCommand
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationId
import java.time.LocalDateTime

data class UpdateReservationRequest(
    val time: LocalDateTime,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val partySize: Int
)

fun UpdateReservationRequest.toCommand(reservationId: ReservationId) = UpdateReservationCommand(
    reservation = Reservation(
        id = reservationId,
        time = time,
        customerDetails = CustomerDetails(
            name = name,
            email = email,
            phoneNumber = phoneNumber
        ),
        partySize = partySize
    )
)