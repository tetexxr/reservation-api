package com.reservation.api.domain.reservations

import java.time.LocalDateTime

data class Reservation(
    val date: LocalDateTime,
    val customerDetails: CustomerDetails,
    val partySize: Int
)

data class CustomerDetails(
    val name: String,
    val email: String,
    val phoneNumber: String
)