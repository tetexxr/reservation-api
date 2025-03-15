package com.reservation.api.application.reservations

import com.reservation.api.application.availability.GetFreeTables
import com.reservation.api.application.availability.GetFreeTablesQuery
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository

class CreateReservation(
    private val getFreeTables: GetFreeTables,
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository
) {
    fun execute(command: CreateReservationCommand): ReservationId {
        val reservation = reservationRepository.insert(command.reservation)
        val query = GetFreeTablesQuery(command.reservation.time, command.reservation.partySize)
        val freeTables = getFreeTables.execute(query)
        if (freeTables.isEmpty()) {
            // TODO waitlist pending to implement
        } else {
            val table = freeTables.first()
            reservationTableRepository.add(reservation.id, table.number)
        }
        return reservation.id
    }
}