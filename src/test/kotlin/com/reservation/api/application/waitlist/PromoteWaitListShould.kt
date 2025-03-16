package com.reservation.api.application.waitlist

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
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class PromoteWaitListShould {

    private val getFreeTables = mock<GetFreeTables>()
    private val reservationRepository = mock<ReservationRepository>()
    private val reservationTableRepository = mock<ReservationTableRepository>()
    private val waitListRepository = mock<WaitListRepository>()
    private val promoteWaitList = PromoteWaitList(
        getFreeTables,
        reservationRepository,
        reservationTableRepository,
        waitListRepository
    )

    @Test
    fun `promote reservations within the given time range and seating capacity`() {
        val command = PromoteWaitListCommand(
            from = LocalDateTime.parse("2021-10-10T10:00:00"),
            to = LocalDateTime.parse("2021-10-10T12:00:00"),
            maximumSeatingCapacity = 4
        )
        val reservation = Reservation.create(
            time = LocalDateTime.parse("2021-10-10T10:30:00"),
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 4
        )
        val freeTable = Table(TableNumber(1), 4)

        whenever(waitListRepository.findAll()).thenReturn(setOf(reservation.id))
        whenever(reservationRepository.findAll()).thenReturn(listOf(reservation))
        whenever(getFreeTables.execute(GetFreeTablesQuery(reservation.time, reservation.partySize)))
            .thenReturn(listOf(freeTable))

        promoteWaitList.execute(command)

        verify(reservationTableRepository).add(reservation.id, freeTable.number)
        verify(waitListRepository).remove(reservation.id)
    }

    @Test
    fun `not promote reservations outside the given time range`() {
        val command = PromoteWaitListCommand(
            from = LocalDateTime.parse("2021-10-10T10:00:00"),
            to = LocalDateTime.parse("2021-10-10T12:00:00"),
            maximumSeatingCapacity = 4
        )
        val reservation = Reservation.create(
            time = LocalDateTime.parse("2021-10-10T12:30:00"),
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 4
        )

        whenever(waitListRepository.findAll()).thenReturn(setOf(reservation.id))
        whenever(reservationRepository.findAll()).thenReturn(listOf(reservation))

        promoteWaitList.execute(command)

        verify(reservationTableRepository, never()).add(eq(reservation.id), any())
        verify(waitListRepository, never()).remove(reservation.id)
        verifyNoInteractions(getFreeTables)
    }

    @Test
    fun `not promote reservations exceeding the seating capacity`() {
        val command = PromoteWaitListCommand(
            from = LocalDateTime.parse("2021-10-10T10:00:00"),
            to = LocalDateTime.parse("2021-10-10T12:00:00"),
            maximumSeatingCapacity = 4
        )
        val reservation = Reservation.create(
            time = LocalDateTime.parse("2021-10-10T10:30:00"),
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 5
        )

        whenever(waitListRepository.findAll()).thenReturn(emptySet())
        whenever(reservationRepository.findAll()).thenReturn(listOf(reservation))

        promoteWaitList.execute(command)

        verify(reservationTableRepository, never()).add(reservation.id, TableNumber(1))
        verify(waitListRepository, never()).remove(reservation.id)
    }

    @Test
    fun `not promote reservations when no free tables are available`() {
        val command = PromoteWaitListCommand(
            from = LocalDateTime.parse("2021-10-10T10:00:00"),
            to = LocalDateTime.parse("2021-10-10T12:00:00"),
            maximumSeatingCapacity = 4
        )
        val reservation = Reservation.create(
            time = LocalDateTime.parse("2021-10-10T10:30:00"),
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 4
        )

        whenever(waitListRepository.findAll()).thenReturn(setOf(reservation.id))
        whenever(reservationRepository.findAll()).thenReturn(listOf(reservation))
        whenever(getFreeTables.execute(GetFreeTablesQuery(reservation.time, reservation.partySize)))
            .thenReturn(emptyList())

        promoteWaitList.execute(command)

        verify(reservationTableRepository, never()).add(reservation.id, TableNumber(1))
        verify(waitListRepository, never()).remove(reservation.id)
    }

    @Test
    fun `not promote reservations when the waitlist is empty`() {
        val command = PromoteWaitListCommand(
            from = LocalDateTime.parse("2021-10-10T10:00:00"),
            to = LocalDateTime.parse("2021-10-10T12:00:00"),
            maximumSeatingCapacity = 4
        )

        whenever(waitListRepository.findAll()).thenReturn(emptySet())

        promoteWaitList.execute(command)

        verifyNoInteractions(reservationRepository)
        verifyNoInteractions(getFreeTables)
    }
}