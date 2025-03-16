package com.reservation.api.domain.reservations

import java.time.LocalDateTime

data class Reservation(
    val id: ReservationId,
    val time: LocalDateTime,
    val customerDetails: CustomerDetails,
    val partySize: Int
) {
    val endTime: LocalDateTime
        get() = time.plusMinutes(RESERVATION_DURATION_MINUTES)

    fun isOverlappingWith(otherTime: LocalDateTime): Boolean {
        val otherEndTime = otherTime.plusMinutes(RESERVATION_DURATION_MINUTES)
        return otherTime >= time && otherTime < endTime || otherEndTime > time && otherEndTime <= endTime
    }

    companion object {
        const val RESERVATION_DURATION_MINUTES: Long = 45
        fun create(
            time: LocalDateTime,
            customerDetails: CustomerDetails,
            partySize: Int
        ) = Reservation(
            id = ReservationId.new(),
            time = time,
            customerDetails = customerDetails,
            partySize = partySize
        )
    }
}

data class CustomerDetails(
    val name: String,
    val email: String,
    val phoneNumber: String
)