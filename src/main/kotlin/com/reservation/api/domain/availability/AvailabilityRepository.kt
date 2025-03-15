package com.reservation.api.domain.availability

import java.time.LocalDateTime

interface AvailabilityRepository {
    fun check(reservationTime: LocalDateTime, partySize: Int): List<Table>
}