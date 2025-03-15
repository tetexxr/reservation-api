package com.reservation.api.infrastructure.controllers.reservations

import com.reservation.api.domain.reservations.Reservation

data class GetReservationsResponse(
    val items: List<ReservationDto>,
    val total: Int = items.size
)

data class ReservationDto(
    val id: String,
    val date: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val partySize: Int
)

fun List<Reservation>.toDto(): GetReservationsResponse {
    val reservations = map {
        ReservationDto(
            id = it.id.value,
            date = it.date.toString(),
            name = it.customerDetails.name,
            email = it.customerDetails.email,
            phoneNumber = it.customerDetails.phoneNumber,
            partySize = it.partySize
        )
    }
    return GetReservationsResponse(reservations)
}