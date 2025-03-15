package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository

class ReservationInMemoryRepository : ReservationRepository {

    override fun insert(reservation: Reservation): Reservation {
        reservations.add(reservation)
        return reservation
    }

    override fun findById(reservationId: ReservationId): Reservation? {
        return reservations.find { it.id == reservationId }
    }

    override fun update(reservation: Reservation) {
        val index = reservations.indexOfFirst { it.id == reservation.id }
        reservations[index] = reservation
    }

    override fun delete(reservationId: ReservationId) {
        reservations.removeIf { it.id == reservationId }
    }
    
    override fun findAll(): List<Reservation> {
        return reservations.toList()
    }

    companion object {
        private val reservations = mutableListOf<Reservation>()
    }
}