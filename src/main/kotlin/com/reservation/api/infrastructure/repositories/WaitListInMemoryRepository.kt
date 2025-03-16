package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.waitlist.WaitListRepository

class WaitListInMemoryRepository : WaitListRepository {

    override fun add(reservationId: ReservationId) {
        waitList.add(reservationId)
    }

    override fun remove(reservationId: ReservationId): Boolean {
        return waitList.remove(reservationId)
    }

    override fun findAll(): Set<ReservationId> {
        return waitList.toSet()
    }

    companion object {
        private val waitList = mutableSetOf<ReservationId>()
    }
}