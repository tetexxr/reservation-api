package com.reservation.api.application.availability

import com.reservation.api.domain.availability.AvailableSlot
import com.reservation.api.domain.reservations.Reservation
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
            var slotTime = opening
            while (slotTime.plusMinutes(SLOT_DURATION) <= closing) {
                val slotEndTime = slotTime.plusMinutes(SLOT_DURATION)
                val slotFilter: (Reservation) -> Boolean = {
                    (slotTime.isBefore(it.endTime) && slotEndTime.isAfter(it.time) && reservationTables[it.id] == table.number)
                }
                val isSlotAvailable = reservations.none { slotFilter(it) }
                if (isSlotAvailable) {
                    availableSlots.add(AvailableSlot(slotTime, slotEndTime, query.partySize, table.number))
                    slotTime = slotEndTime
                } else {
                    val endTime = reservations.single { slotFilter(it) }.endTime
                    slotTime = endTime
                }
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