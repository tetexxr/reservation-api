package com.reservation.api.domain.reservations

import java.util.UUID

data class ReservationId(
    val value: String
) {
    companion object {
        fun new() = ReservationId(UUID.randomUUID().toString())
    }
}