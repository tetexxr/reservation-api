package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository

class CancelReservation(
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository
) {
    fun execute(command: CancelReservationCommand) {
        reservationRepository.delete(command.reservationId)
        reservationTableRepository.remove(command.reservationId)
    }
}