package com.reservation.api.application.reservations

import com.reservation.api.application.waitlist.PromoteWaitList
import com.reservation.api.application.waitlist.PromoteWaitListCommand
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository

class CancelReservation(
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository,
    private val waitListRepository: WaitListRepository,
    private val promoteWaitList: PromoteWaitList
) {
    fun execute(command: CancelReservationCommand) {
        val reservation = reservationRepository.findById(command.reservationId)
        reservationRepository.delete(command.reservationId)
        reservationTableRepository.remove(command.reservationId)
        val isInWaitList = waitListRepository.remove(command.reservationId)
        if (reservation != null && !isInWaitList) {
            promoteWaitList.execute(PromoteWaitListCommand.create(reservation))
        }
    }
}