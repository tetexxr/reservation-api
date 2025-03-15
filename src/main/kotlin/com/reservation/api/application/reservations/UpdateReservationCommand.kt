package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.Reservation

data class UpdateReservationCommand(
    val reservation: Reservation
)