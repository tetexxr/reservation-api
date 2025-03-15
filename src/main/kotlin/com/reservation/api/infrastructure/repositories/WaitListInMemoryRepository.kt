package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.waitlist.WaitListRepository

class WaitListInMemoryRepository : WaitListRepository {

    override fun add(reservationId: ReservationId) {
        waitList.add(reservationId)
    }

    override fun remove(reservationId: ReservationId) {
        waitList.remove(reservationId)
    }

    companion object {
        private val waitList = mutableSetOf<ReservationId>()
    }
}