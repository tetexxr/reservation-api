package com.reservation.api.helpers

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.waitlist.WaitListRepository
import org.springframework.stereotype.Component

@Component
class Cleaner(
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository,
    private val waitListRepository: WaitListRepository
) {
    fun execute() {
        reservationRepository.findAll().forEach {
            reservationRepository.delete(it.id)
        }
        reservationTableRepository.findAll().forEach {
            reservationTableRepository.remove(it.key)
        }
        waitListRepository.findAll().forEach {
            waitListRepository.remove(it)
        }
    }
}