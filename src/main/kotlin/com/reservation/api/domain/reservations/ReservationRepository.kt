package com.reservation.api.domain.reservations

import com.reservation.api.domain.tables.TableNumber

interface ReservationRepository {
    fun insert(reservation: Reservation): Reservation
    fun findById(reservationId: ReservationId): Reservation?
    fun update(reservation: Reservation)
    fun delete(reservationId: ReservationId)
    fun findAll(): List<Reservation>
    fun findAllReservationTables(): Map<ReservationId, TableNumber>
}