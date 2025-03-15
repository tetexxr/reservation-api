package com.reservation.api.domain.reservations

import java.time.LocalDateTime

data class Reservation(
    val id: ReservationId,
    val date: LocalDateTime,
    val customerDetails: CustomerDetails,
    val partySize: Int
) {
    companion object {
        fun create(
            date: LocalDateTime,
            customerDetails: CustomerDetails,
            partySize: Int
        ) = Reservation(
            id = ReservationId.new(),
            date = date,
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