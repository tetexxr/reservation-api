package com.reservation.api.domain.reservations

import java.time.LocalDateTime

data class Reservation(
    val id: ReservationId,
    val time: LocalDateTime,
    val customerDetails: CustomerDetails,
    val partySize: Int
) {
    companion object {
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