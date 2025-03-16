package com.reservation.api.application.availability

import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.Table
import com.reservation.api.domain.tables.TableNumber
import com.reservation.api.domain.tables.TableRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class GetFreeTablesShould {

    private val tableRepository = mock<TableRepository>()
    private val reservationRepository = mock<ReservationRepository>()
    private val reservationTableRepository = mock<ReservationTableRepository>()
    private val getFreeTables = GetFreeTables(tableRepository, reservationRepository, reservationTableRepository)

    @Test
    fun `return all suitable tables when there are no reservations`() {
        val tables = listOf(
            Table(TableNumber(1), 2),
            Table(TableNumber(2), 4),
            Table(TableNumber(3), 4),
            Table(TableNumber(4), 6)
        )
        whenever(tableRepository.findAll()).thenReturn(tables)
        whenever(reservationRepository.findAll()).thenReturn(emptyList())
        whenever(reservationTableRepository.findAll()).thenReturn(emptyMap())

        val query = GetFreeTablesQuery(LocalDateTime.parse("2021-10-10T10:00:00"), 4)
        val result = getFreeTables.execute(query)

        assertThat(result).containsExactly(
            Table(TableNumber(2), 4),
            Table(TableNumber(3), 4)
        )
    }

    @Test
    fun `return only suitable tables for the party size`() {
        val tables = listOf(
            Table(TableNumber(1), 4),
            Table(TableNumber(2), 6)
        )
        whenever(tableRepository.findAll()).thenReturn(tables)
        whenever(reservationRepository.findAll()).thenReturn(emptyList())
        whenever(reservationTableRepository.findAll()).thenReturn(emptyMap())

        val query = GetFreeTablesQuery(LocalDateTime.parse("2021-10-10T10:00:00"), 3)
        val result = getFreeTables.execute(query)

        assertThat(result).containsExactly(
            Table(TableNumber(1), 4)
        )
    }

    @Test
    fun `exclude reserved tables`() {
        val tables = listOf(
            Table(TableNumber(1), 4),
            Table(TableNumber(2), 6)
        )
        val reservations = listOf(
            Reservation.create(
                LocalDateTime.parse("2021-10-10T10:00:00"),
                CustomerDetails("John", "john@test.com", "931111111"),
                4
            )
        )
        val reservedTables = mapOf(reservations[0].id to TableNumber(1))

        whenever(tableRepository.findAll()).thenReturn(tables)
        whenever(reservationRepository.findAll()).thenReturn(reservations)
        whenever(reservationTableRepository.findAll()).thenReturn(reservedTables)

        val query = GetFreeTablesQuery(LocalDateTime.parse("2021-10-10T10:00:00"), 4)
        val result = getFreeTables.execute(query)

        assertThat(result).isEmpty()
    }

    @Test
    fun `return empty list when no tables are suitable`() {
        val tables = listOf(
            Table(TableNumber(1), 4),
            Table(TableNumber(2), 6)
        )
        whenever(tableRepository.findAll()).thenReturn(tables)
        whenever(reservationRepository.findAll()).thenReturn(emptyList())
        whenever(reservationTableRepository.findAll()).thenReturn(emptyMap())

        val query = GetFreeTablesQuery(LocalDateTime.parse("2021-10-10T10:00:00"), 8)
        val result = getFreeTables.execute(query)

        assertThat(result).isEmpty()
    }
}