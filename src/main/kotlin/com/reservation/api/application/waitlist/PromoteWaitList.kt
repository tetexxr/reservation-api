package com.reservation.api.application.waitlist

import com.reservation.api.application.availability.GetFreeTables
import com.reservation.api.application.availability.GetFreeTablesQuery
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository

class PromoteWaitList(
    private val getFreeTables: GetFreeTables,
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository,
    private val waitListRepository: WaitListRepository
) {
    fun execute(command: PromoteWaitListCommand) {
        val waitList = waitListRepository.findAll()
        val reservationsWaiting = reservationRepository.findAll()
            .filter {
                it.time.isAfter(command.from) && it.endTime.isBefore(command.to) && it.partySize <= command.maximumSeatingCapacity
            }
            .filter {
                waitList.contains(it.id)
            }

        reservationsWaiting.forEach { reservation ->
            val freeTables = getFreeTables.execute(GetFreeTablesQuery(reservation.time, reservation.partySize))
            if (freeTables.isNotEmpty()) {
                reservationTableRepository.add(reservation.id, freeTables.first().number)
                waitListRepository.remove(reservation.id)

                // This can be replaced sending a notification to the customer
                println("Promoted reservation ${reservation.id} to table ${freeTables.first().number}")
            }
        }
    }
}