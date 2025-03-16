package com.reservation.api.application.notifications

import com.reservation.api.domain.notifications.NotificationRepository
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class SendNotification(
    private val reservationRepository: ReservationRepository,
    private val reservationTableRepository: ReservationTableRepository,
    private val notificationRepository: NotificationRepository
) {
    fun execute() {
        val reservations = reservationRepository.findAll().filter {
            it.time.truncatedTo(ChronoUnit.MINUTES) == LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES)
        }
        reservations.forEach { reservation ->
            val tableNumber = reservationTableRepository.findById(reservation.id)
            if (tableNumber != null) {
                notificationRepository.notify(reservation)
            }
        }
    }
}