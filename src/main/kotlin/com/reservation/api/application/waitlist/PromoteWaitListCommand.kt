package com.reservation.api.application.waitlist

import java.time.LocalDateTime

data class PromoteWaitListCommand(
    val from: LocalDateTime,
    val to: LocalDateTime,
    val maximumSeatingCapacity: Int
)