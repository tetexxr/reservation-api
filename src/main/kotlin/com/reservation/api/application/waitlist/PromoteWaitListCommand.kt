package com.reservation.api.application.waitlist

import com.reservation.api.domain.reservations.Reservation
import java.time.LocalDateTime

data class PromoteWaitListCommand(
    val from: LocalDateTime,
    val to: LocalDateTime,
    val maximumSeatingCapacity: Int
) {
    companion object {
        fun create(reservation: Reservation): PromoteWaitListCommand {
            return PromoteWaitListCommand(
                from = reservation.time.minusMinutes(Reservation.RESERVATION_DURATION_MINUTES),
                to = reservation.endTime.plusMinutes(Reservation.RESERVATION_DURATION_MINUTES),
                maximumSeatingCapacity = reservation.partySize
            )
        }
    }
}