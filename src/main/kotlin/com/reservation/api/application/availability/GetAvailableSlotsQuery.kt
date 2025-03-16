package com.reservation.api.application.availability

import java.time.LocalDate

data class GetAvailableSlotsQuery(
    val date: LocalDate,
    val partySize: Int
)