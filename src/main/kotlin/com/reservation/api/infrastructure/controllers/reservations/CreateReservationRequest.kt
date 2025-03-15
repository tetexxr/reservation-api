package com.reservation.api.infrastructure.controllers.reservations

import com.reservation.api.application.reservations.CreateReservationCommand
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import java.time.LocalDateTime

data class CreateReservationRequest(
    val date: LocalDateTime,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val partySize: Int
)

fun CreateReservationRequest.toCommand() = CreateReservationCommand(
    reservation = Reservation.create(
        date = date,
        customerDetails = CustomerDetails(
            name = name,
            email = email,
            phoneNumber = phoneNumber
        ),
        partySize = partySize
    )
)