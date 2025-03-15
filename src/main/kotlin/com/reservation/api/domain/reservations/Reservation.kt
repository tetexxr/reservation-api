package com.reservation.api.domain.reservations

import java.time.LocalDateTime
import java.util.*

data class Reservation(
    val id: String,
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
            id = UUID.randomUUID().toString(),
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