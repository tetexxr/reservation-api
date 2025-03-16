package com.reservation.api.infrastructure.controllers.availability

import com.reservation.api.domain.availability.AvailableSlot
import java.time.LocalDateTime

data class GetAvailableSlotsResponse(
    val items: List<AvailableSlotDto>,
    val total: Int = items.size
)

data class AvailableSlotDto(
    val from: LocalDateTime,
    val to: LocalDateTime,
    val tableNumber: Int
)

fun List<AvailableSlot>.toDto(): GetAvailableSlotsResponse {
    val availableSlots = map {
        AvailableSlotDto(
            from = it.from,
            to = it.to,
            tableNumber = it.tableNumber.value
        )
    }
    return GetAvailableSlotsResponse(availableSlots)
}