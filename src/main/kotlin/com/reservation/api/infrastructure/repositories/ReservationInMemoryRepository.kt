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

    companion object {
        private val reservations = mutableListOf<Reservation>()
    }
}