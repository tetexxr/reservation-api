package com.reservation.api.infrastructure.configuration

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.infrastructure.repositories.ReservationInMemoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfiguration {

    @Bean
    fun reservationRepository(): ReservationRepository {
        return ReservationInMemoryRepository()
    }
}