package com.reservation.api.repositories

import com.reservation.api.Application
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.infrastructure.repositories.WaitListInMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
class WaitListInMemoryRepositoryShould {

    @Autowired
    private lateinit var repository: WaitListInMemoryRepository

    @BeforeEach
    fun setUp() {
        repository.findAll().forEach {
            repository.remove(it)
        }
    }

    @Test
    fun `add a reservation to the waitlist`() {
        val reservationId = ReservationId.new()

        repository.add(reservationId)

        assertThat(repository.findAll()).containsExactly(reservationId)
    }

    @Test
    fun `remove a reservation from the waitlist`() {
        val reservationId = ReservationId.new()
        repository.add(reservationId)

        val isRemoved = repository.remove(reservationId)

        assertThat(repository.findAll()).isEmpty()
        assertThat(isRemoved).isTrue()
    }

    @Test
    fun `retrieve all reservations from the waitlist`() {
        val reservationId1 = ReservationId.new()
        val reservationId2 = ReservationId.new()
        repository.add(reservationId1)
        repository.add(reservationId2)

        val waitList = repository.findAll()

        assertThat(waitList.size).isEqualTo(2)
        assertThat(waitList).containsExactly(reservationId1, reservationId2)
    }
}