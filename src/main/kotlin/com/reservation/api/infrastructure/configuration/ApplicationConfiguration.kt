package com.reservation.api.infrastructure.configuration

import com.reservation.api.application.availability.GetFreeTables
import com.reservation.api.application.notifications.SendNotification
import com.reservation.api.application.reservations.CancelReservation
import com.reservation.api.application.reservations.CreateReservation
import com.reservation.api.application.reservations.UpdateReservation
import com.reservation.api.domain.notifications.NotificationRepository
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.TableRepository
import com.reservation.api.domain.waitlist.WaitListRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun getFreeTables(
        tableRepository: TableRepository,
        reservationRepository: ReservationRepository,
        reservationTableRepository: ReservationTableRepository
    ) = GetFreeTables(tableRepository, reservationRepository, reservationTableRepository)

    @Bean
    fun createReservation(
        getFreeTables: GetFreeTables,
        reservationRepository: ReservationRepository,
        reservationTableRepository: ReservationTableRepository,
        waitListRepository: WaitListRepository
    ) = CreateReservation(getFreeTables, reservationRepository, reservationTableRepository, waitListRepository)

    @Bean
    fun updateReservation(
        getFreeTables: GetFreeTables,
        reservationRepository: ReservationRepository,
        reservationTableRepository: ReservationTableRepository,
        waitListRepository: WaitListRepository
    ) = UpdateReservation(getFreeTables, reservationRepository, reservationTableRepository, waitListRepository)

    @Bean
    fun cancelReservation(
        reservationRepository: ReservationRepository,
        reservationTableRepository: ReservationTableRepository,
        waitListRepository: WaitListRepository
    ) = CancelReservation(reservationRepository, reservationTableRepository, waitListRepository)

    @Bean
    fun sendNotification(
        reservationRepository: ReservationRepository,
        reservationTableRepository: ReservationTableRepository,
        notificationRepository: NotificationRepository
    ) = SendNotification(reservationRepository, reservationTableRepository, notificationRepository)
}