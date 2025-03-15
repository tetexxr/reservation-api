package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CancelReservationShould {

    @Test
    fun `cancel a reservation`() {
        val reservationId = ReservationId.new()
        val reservationRepository = mock<ReservationRepository>()
        val reservationTableRepository = mock<ReservationTableRepository>()
        val waitListRepository = mock<WaitListRepository>()
        val cancelReservation = CancelReservation(reservationRepository, reservationTableRepository, waitListRepository)
        val command = CancelReservationCommand(reservationId)

        cancelReservation.execute(command)

        verify(reservationRepository).delete(reservationId)
        verify(reservationTableRepository).remove(reservationId)
        verify(waitListRepository).remove(reservationId)
    }
}