package com.reservation.api.application.reservations

import com.reservation.api.application.availability.GetFreeTables
import com.reservation.api.application.availability.GetFreeTablesQuery
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository

class UpdateReservation(
    private val getFreeTables: GetFreeTables,
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository,
    private val waitListRepository: WaitListRepository
) {
    fun execute(command: UpdateReservationCommand) {
        reservationTableRepository.remove(command.reservation.id)
        waitListRepository.remove(command.reservation.id)
        reservationRepository.update(command.reservation)
        val query = GetFreeTablesQuery(command.reservation.time, command.reservation.partySize)
        val freeTables = getFreeTables.execute(query)
        if (freeTables.isEmpty()) {
            waitListRepository.add(command.reservation.id)
        } else {
            val table = freeTables.first()
            reservationTableRepository.add(command.reservation.id, table.number)
        }
    }
}