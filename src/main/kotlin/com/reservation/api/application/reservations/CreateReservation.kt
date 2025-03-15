package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationRepository

class CreateReservation(
    private val reservationRepository: ReservationRepository
) {
    fun execute(command: CreateReservationCommand) {
        reservationRepository.insert(command.reservation)
        // Send metrics to monitoring system
        // Send notification to customer
        // Other stuff
    }
}