package com.reservation.api.domain.reservations

import com.reservation.api.domain.tables.TableNumber

interface ReservationTableRepository {
    fun add(reservationId: ReservationId, tableNumber: TableNumber)
    fun remove(reservationId: ReservationId)
    fun findAll(): Map<ReservationId, TableNumber>
    fun findById(reservationId: ReservationId): TableNumber?
}