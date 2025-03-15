package com.reservation.api.infrastructure.configuration

import com.reservation.api.application.availability.GetFreeTables
import com.reservation.api.application.reservations.CreateReservation
import com.reservation.api.application.reservations.DeleteReservation
import com.reservation.api.application.reservations.UpdateReservation
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.TableRepository
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
        reservationTableRepository: ReservationTableRepository
    ) = CreateReservation(getFreeTables, reservationRepository, reservationTableRepository)

    @Bean
    fun updateReservation(reservationRepository: ReservationRepository) = UpdateReservation(reservationRepository)

    @Bean
    fun deleteReservation(
        reservationRepository: ReservationRepository,
        reservationTableRepository: ReservationTableRepository
    ) = DeleteReservation(reservationRepository, reservationTableRepository)
}