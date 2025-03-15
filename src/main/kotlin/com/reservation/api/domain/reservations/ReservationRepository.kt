package com.reservation.api.domain.reservations

interface ReservationRepository {
    fun insert(reservation: Reservation): Reservation
    fun findById(reservationId: ReservationId): Reservation?
}