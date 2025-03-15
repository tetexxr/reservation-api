package com.reservation.api.infrastructure.configuration

import com.reservation.api.application.reservations.CreateReservation
import com.reservation.api.application.reservations.UpdateReservation
import com.reservation.api.domain.reservations.ReservationRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun createReservation(reservationRepository: ReservationRepository) = CreateReservation(reservationRepository)
    
    @Bean
    fun updateReservation(reservationRepository: ReservationRepository) = UpdateReservation(reservationRepository)
}