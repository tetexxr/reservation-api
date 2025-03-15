package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteReservationShould {

    @Test
    fun `delete a reservation`() {
        val reservationId = ReservationId.new()
        val reservationRepository = mock<ReservationRepository>()
        val deleteReservation = DeleteReservation(reservationRepository)
        val command = DeleteReservationCommand(reservationId)

        deleteReservation.execute(command)

        verify(reservationRepository).delete(reservationId)
    }
}