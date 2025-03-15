package com.reservation.api.domain.reservations

import com.reservation.api.domain.tables.TableNumber

interface ReservationTableRepository {
    fun findAll(): Map<ReservationId, TableNumber>
    fun add(reservationId: ReservationId, tableNumber: TableNumber)
    fun remove(reservationId: ReservationId)
}