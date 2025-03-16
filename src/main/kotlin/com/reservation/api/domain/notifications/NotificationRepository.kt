package com.reservation.api.domain.notifications

import com.reservation.api.domain.reservations.Reservation

interface NotificationRepository {
    fun notify(reservation: Reservation)
}