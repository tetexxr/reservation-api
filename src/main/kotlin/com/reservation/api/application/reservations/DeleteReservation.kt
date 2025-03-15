package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository

class DeleteReservation(
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository
) {
    fun execute(command: DeleteReservationCommand) {
        reservationRepository.delete(command.reservationId)
        reservationTableRepository.remove(command.reservationId)
    }
}