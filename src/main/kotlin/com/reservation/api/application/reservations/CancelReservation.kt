package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository

class CancelReservation(
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository,
    private val waitListRepository: WaitListRepository
) {
    fun execute(command: CancelReservationCommand) {
        reservationRepository.delete(command.reservationId)
        reservationTableRepository.remove(command.reservationId)
        waitListRepository.remove(command.reservationId)
    }
}