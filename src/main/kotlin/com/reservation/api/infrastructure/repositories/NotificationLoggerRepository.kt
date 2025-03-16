package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.notifications.NotificationRepository
import com.reservation.api.domain.reservations.Reservation

class NotificationLoggerRepository : NotificationRepository {
    override fun notify(reservation: Reservation) {
        println("Hi ${reservation.customerDetails.name}, your reservation is confirmed at ${reservation.time}.")
    }
}