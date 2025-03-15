package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationId

data class CancelReservationCommand(
    val reservationId: ReservationId
)