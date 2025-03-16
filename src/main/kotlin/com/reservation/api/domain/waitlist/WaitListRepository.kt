package com.reservation.api.domain.waitlist

import com.reservation.api.domain.reservations.ReservationId

interface WaitListRepository {
    fun add(reservationId: ReservationId)
    fun remove(reservationId: ReservationId): Boolean
    fun findAll(): Set<ReservationId>
}