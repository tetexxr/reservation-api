package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.TableNumber

class ReservationTableInMemoryRepository : ReservationTableRepository {

    override fun add(reservationId: ReservationId, tableNumber: TableNumber) {
        reservationTables[reservationId] = tableNumber
    }

    override fun remove(reservationId: ReservationId) {
        reservationTables.remove(reservationId)
    }

    override fun findAll(): Map<ReservationId, TableNumber> {
        return reservationTables.toMap()
    }

    override fun findById(reservationId: ReservationId): TableNumber? {
        return reservationTables[reservationId]
    }

    companion object {
        private val reservationTables = mutableMapOf<ReservationId, TableNumber>()
    }
}