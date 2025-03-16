package com.reservation.api.domain.availability

import java.time.LocalDateTime

data class SlotAvailable(
    val from: LocalDateTime,
    val to: LocalDateTime,
    val partySize: Int
)