package com.reservation.api.application.availability

import java.time.LocalDate

data class GetSlotsAvailableQuery(
    val date: LocalDate,
    val partySize: Int
)