package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationRepository

class DeleteReservation(
    private val reservationRepository: ReservationRepository
) {
    fun execute(command: DeleteReservationCommand) {
        reservationRepository.delete(command.reservationId)
        // Send metrics to monitoring system
        // Send notification to customer
        // Other stuff
    }
}