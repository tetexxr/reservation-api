package com.reservation.api.domain.reservations

interface ReservationRepository {
    fun insert(reservation: Reservation): Reservation
    fun findById(reservationId: ReservationId): Reservation?
    fun update(reservation: Reservation)
    fun delete(reservationId: ReservationId)
    fun findAll(): List<Reservation>
}