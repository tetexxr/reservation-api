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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import java.time.LocalDateTime

class CreateReservationShould {

    @Test
    fun `create a reservation and assign to a table`() {
        val reservation = Reservation.create(
            time = LocalDateTime.parse("2021-10-10T10:00:00"),
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
        val reservationRepository = mock<ReservationRepository> {
            on { insert(reservation) }.thenReturn(reservation)
        }
        val reservationTableRepository = mock<ReservationTableRepository>()
        val waitListRepository = mock<WaitListRepository>()
        val createReservation = CreateReservation(
            getFreeTables,
            reservationRepository,
            reservationTableRepository,
            waitListRepository
        )
        val command = CreateReservationCommand(reservation)

        val id = createReservation.execute(command)

        verify(reservationRepository).insert(reservation)
        verify(reservationTableRepository).add(reservation.id, TableNumber(1))
        verifyNoInteractions(waitListRepository)
        assertThat(id.value).isNotEmpty()
    }
}