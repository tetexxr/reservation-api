package com.reservation.api.domain.reservations

interface ReservationRepository {
    fun create(reservation: Reservation): Reservation
    fun findById(reservationId: String): Reservation?
}