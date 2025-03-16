package com.reservation.api.domain.availability

import com.reservation.api.domain.tables.TableNumber
import java.time.LocalDateTime

data class AvailableSlot(
    val from: LocalDateTime,
    val to: LocalDateTime,
    val partySize: Int,
    val tableNumber: TableNumber
)