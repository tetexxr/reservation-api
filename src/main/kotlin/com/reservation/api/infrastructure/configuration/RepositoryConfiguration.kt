package com.reservation.api.infrastructure.configuration

import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.tables.TableRepository
import com.reservation.api.infrastructure.repositories.ReservationInMemoryRepository
import com.reservation.api.infrastructure.repositories.TableInMemoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfiguration {

    @Bean
    fun reservationRepository(): ReservationRepository = ReservationInMemoryRepository()

    @Bean
    fun tableRepository(): TableRepository = TableInMemoryRepository()
}