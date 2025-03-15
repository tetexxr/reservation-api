package com.reservation.api.application.reservations

import com.reservation.api.application.availability.GetFreeTables
import com.reservation.api.application.availability.GetFreeTablesQuery
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.Table
import com.reservation.api.domain.tables.TableNumber
import com.reservation.api.domain.waitlist.WaitListRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import java.time.LocalDateTime

class UpdateReservationShould {

    @Test
    fun `update a reservation and reassign to a table`() {
        val reservation = Reservation.create(
            time = LocalDateTime.now(),
            customerDetails = CustomerDetails(
                name = "John",
                email = "john@test.com",
                phoneNumber = "931111111"
            ),
            partySize = 4
        )
        val getFreeTables = mock<GetFreeTables> {
            on { execute(GetFreeTablesQuery(reservation.time, reservation.partySize)) }
                .thenReturn(listOf(Table(TableNumber(1), 4)))
        }
        val reservationRepository = mock<ReservationRepository>()
        val reservationTableRepository = mock<ReservationTableRepository>()
        val waitListRepository = mock<WaitListRepository>()
        val updateReservation = UpdateReservation(
            getFreeTables,
            reservationRepository,
            reservationTableRepository,
            waitListRepository
        )
        val command = UpdateReservationCommand(reservation)

        updateReservation.execute(command)

        verify(reservationTableRepository).remove(reservation.id)
        verify(waitListRepository).remove(reservation.id)
        verify(reservationRepository).update(reservation)
        verify(reservationTableRepository).add(reservation.id, TableNumber(1))
        verifyNoMoreInteractions(waitListRepository)
    }
}