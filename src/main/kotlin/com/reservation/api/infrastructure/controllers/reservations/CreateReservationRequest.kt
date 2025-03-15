package com.reservation.api.infrastructure.controllers.reservations

data class CreateReservationRequest(
    val date: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val partySize: Int
)