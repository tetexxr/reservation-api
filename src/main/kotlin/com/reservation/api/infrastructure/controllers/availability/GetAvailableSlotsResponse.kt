package com.reservation.api.infrastructure.controllers.availability

import com.reservation.api.domain.availability.AvailableSlot

data class GetAvailableSlotsResponse(
    val items: List<AvailableSlotDto>,
    val total: Int = items.size
)

data class AvailableSlotDto(
    val from: String,
    val to: String,
    val tableNumber: Int
)

fun List<AvailableSlot>.toDto(): GetAvailableSlotsResponse {
    val availableSlots = map {
        AvailableSlotDto(
            from = it.from.toString(),
            to = it.to.toString(),
            tableNumber = it.tableNumber.value
        )
    }
    return GetAvailableSlotsResponse(availableSlots)
}