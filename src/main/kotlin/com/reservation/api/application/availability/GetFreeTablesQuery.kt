package com.reservation.api.application.availability

import java.time.LocalDateTime

data class GetFreeTablesQuery(
    val reservationTime: LocalDateTime,
    val partySize: Int
)