package com.reservation.api.application.availability

import com.reservation.api.domain.availability.AvailableSlot
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.TableRepository

class GetAvailableSlots(
    private val tableRepository: TableRepository,
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository
) {
    fun execute(query: GetAvailableSlotsQuery): List<AvailableSlot> {
        val tables = tableRepository.findAll()
            .filter { it.isSuitableForPartySize(query.partySize) }
            .sortedBy { it.number.value }
        val reservationTables = reservationTableRepository.findAll()
        val reservations = reservationRepository.findAll()
            .filter { it.time.toLocalDate() == query.date }
            .filter { tables.any { table -> table.number == reservationTables[it.id] } }
            .sortedBy { it.time }

        val opening = query.date.atTime(OPENING_HOUR, 0, 0)
        val closing = query.date.atTime(CLOSING_HOUR, 0, 0)
        val availableSlots = mutableListOf<AvailableSlot>()

        tables.forEach { table ->
            var currentTime = opening
            while (currentTime.isBefore(closing)) {
                val slotEndTime = currentTime.plusMinutes(SLOT_DURATION)
                val isSlotAvailable = reservations.none {
                    currentTime.isBefore(it.endTime)
                            && slotEndTime.isAfter(it.time)
                            && reservationTables[it.id] == table.number
                }
                if (isSlotAvailable) {
                    availableSlots.add(AvailableSlot(currentTime, slotEndTime, query.partySize, table.number))
                } else {
                    currentTime = reservations
                        .filter { reservationTables[it.id] == table.number && it.time.isAfter(currentTime) }
                        .minOfOrNull { it.endTime } ?: slotEndTime
                }
                currentTime = slotEndTime
            }
        }

        return availableSlots
    }

    companion object {
        private const val SLOT_DURATION: Long = 15
        private const val OPENING_HOUR = 8
        private const val CLOSING_HOUR = 14
    }
}