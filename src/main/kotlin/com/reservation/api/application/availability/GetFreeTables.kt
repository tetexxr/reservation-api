package com.reservation.api.application.availability

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.tables.Table
import com.reservation.api.domain.tables.TableRepository

class GetFreeTables(
    private val tableRepository: TableRepository,
    private val reservationRepository: ReservationRepository
) {
    fun execute(query: GetFreeTablesQuery): List<Table> {
        val tables = tableRepository.findAll()
        val overlappingReservations = reservationRepository.findAll()
            .filter { !it.isOverlappingWith(query.reservationTime) }
        val reservedTables = reservationRepository.findAllReservationTables()
            .filter { it.key in overlappingReservations.map { reservation -> reservation.id } }
            .map { it.value }
        val freeTables = tables
            .filter { table -> reservedTables.none { it == table.number } }
            .filter { it.isSuitableForPartySize(query.partySize) }
        return freeTables
    }
}