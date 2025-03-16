package com.reservation.api.application.availability

import com.reservation.api.domain.availability.AvailableSlot
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.Table
import com.reservation.api.domain.tables.TableNumber
import com.reservation.api.domain.tables.TableRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime

class GetAvailableSlotsShould {

    private val tableRepository = mock<TableRepository>()
    private val reservationRepository = mock<ReservationRepository>()
    private val reservationTableRepository = mock<ReservationTableRepository>()
    private val getAvailableSlots = GetAvailableSlots(
        tableRepository,
        reservationRepository,
        reservationTableRepository
    )

    @Test
    fun `return all slots when there are no reservations`() {
        val tables = listOf(
            Table(TableNumber(1), 4),
            Table(TableNumber(2), 4),
            Table(TableNumber(3), 6),
            Table(TableNumber(4), 8)
        )
        whenever(tableRepository.findAll()).thenReturn(tables)
        whenever(reservationRepository.findAll()).thenReturn(emptyList())
        whenever(reservationTableRepository.findAll()).thenReturn(emptyMap())

        val query = GetAvailableSlotsQuery(LocalDate.parse("2021-10-10"), 4)
        val availableSlots = getAvailableSlots.execute(query)

        // 24 slots per table (15 min slots from 8:00 to 14:00), for 2 tables with party size 4
        assertThat(availableSlots).hasSize(24 * 2)
        assertThat(availableSlots).contains(
            AvailableSlot(
                LocalDateTime.parse("2021-10-10T08:00:00"),
                LocalDateTime.parse("2021-10-10T08:15:00"),
                4,
                TableNumber(1)
            ),
            AvailableSlot(
                LocalDateTime.parse("2021-10-10T08:00:00"),
                LocalDateTime.parse("2021-10-10T08:15:00"),
                4,
                TableNumber(2)
            ),
            AvailableSlot(
                LocalDateTime.parse("2021-10-10T13:45:00"),
                LocalDateTime.parse("2021-10-10T14:00:00"),
                4,
                TableNumber(1)
            ),
            AvailableSlot(
                LocalDateTime.parse("2021-10-10T13:45:00"),
                LocalDateTime.parse("2021-10-10T14:00:00"),
                4,
                TableNumber(2)
            )
        )
    }

    @Test
    fun `not return slots when all day is completely reserved`() {
        val tables = listOf(
            Table(TableNumber(1), 4)
        )
        val opening = LocalDateTime.parse("2021-10-10T08:00:00")
        val reservations = (0..23).map {
            val time = opening.plusMinutes(45 * it.toLong())
            reservationAt(time)
        }
        val reservedTables = reservations.map { it.id }.associateWith { TableNumber(1) }

        whenever(tableRepository.findAll()).thenReturn(tables)
        whenever(reservationRepository.findAll()).thenReturn(reservations)
        whenever(reservationTableRepository.findAll()).thenReturn(reservedTables)

        val query = GetAvailableSlotsQuery(LocalDate.parse("2021-10-10"), 4)
        val availableSlots = getAvailableSlots.execute(query)

        assertThat(availableSlots).isEmpty()
    }

    companion object {
        private fun reservationAt(time: LocalDateTime) = Reservation.create(
            time = time,
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 4
        )
    }
}