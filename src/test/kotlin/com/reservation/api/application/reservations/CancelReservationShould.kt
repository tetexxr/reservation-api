package com.reservation.api.application.reservations

import com.reservation.api.application.waitlist.PromoteWaitList
import com.reservation.api.application.waitlist.PromoteWaitListCommand
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class CancelReservationShould {

    @Test
    fun `cancel a reservation`() {
        val reservationId = ReservationId.new()
        val reservationRepository = mock<ReservationRepository>()
        val reservationTableRepository = mock<ReservationTableRepository>()
        val waitListRepository = mock<WaitListRepository>()
        val promoteWaitList = mock<PromoteWaitList>()
        val cancelReservation = CancelReservation(
            reservationRepository,
            reservationTableRepository,
            waitListRepository,
            promoteWaitList
        )
        val command = CancelReservationCommand(reservationId)

        cancelReservation.execute(command)

        verify(reservationRepository).delete(reservationId)
        verify(reservationTableRepository).remove(reservationId)
        verify(waitListRepository).remove(reservationId)
    }

    @Test
    fun `promote reservation from waitlist when cancelling a reservation that is assigned to a table`() {
        val reservation = Reservation.create(
            time = LocalDateTime.parse("2022-01-01T12:00:00"),
            CustomerDetails("John", "john@test.com", "931111111"),
            4
        )
        val reservationRepository = mock<ReservationRepository> {
            on { findById(reservation.id) }.thenReturn(reservation)
        }
        val reservationTableRepository = mock<ReservationTableRepository>()
        val waitListRepository = mock<WaitListRepository> {
            on { remove(reservation.id) }.thenReturn(false)
        }
        val promoteWaitList = mock<PromoteWaitList>()
        val cancelReservation = CancelReservation(
            reservationRepository,
            reservationTableRepository,
            waitListRepository,
            promoteWaitList
        )
        val command = CancelReservationCommand(reservation.id)

        cancelReservation.execute(command)

        verify(promoteWaitList).execute(PromoteWaitListCommand.create(reservation))
    }
}