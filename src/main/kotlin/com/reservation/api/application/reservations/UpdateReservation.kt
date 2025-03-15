package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationRepository

class UpdateReservation(
    private val reservationRepository: ReservationRepository
) {
    fun execute(command: UpdateReservationCommand) {
        reservationRepository.update(command.reservation)
        // Send metrics to monitoring system
        // Send notification to customer
        // Other stuff
    }
}