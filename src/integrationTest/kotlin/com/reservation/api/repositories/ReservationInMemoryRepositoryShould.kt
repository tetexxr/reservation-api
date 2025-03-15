package com.reservation.api.repositories

import com.reservation.api.Application
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.infrastructure.repositories.ReservationInMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
class ReservationInMemoryRepositoryShould {

    @Autowired
    private lateinit var repository: ReservationInMemoryRepository

    @Test
    fun `insert a reservation`() {
        val reservation = Reservation.create(
            date = LocalDateTime.now(),
            customerDetails = CustomerDetails(
                name = "John",
                email = "john@test.com",
                phoneNumber = "931111111"
            ),
            partySize = 4
        )

        repository.insert(reservation)

        assertThat(repository.findById(reservation.id)).isEqualTo(reservation)
    }

    @Test
    fun `update a reservation`() {
        val reservation = Reservation.create(
            date = LocalDateTime.now(),
            customerDetails = CustomerDetails(
                name = "John",
                email = "john@test.com",
                phoneNumber = "931111111"
            ),
            partySize = 4
        )
        repository.insert(reservation)

        val updatedReservation = reservation.copy(
            customerDetails = reservation.customerDetails.copy(name = "John Doe")
        )
        repository.update(updatedReservation)

        assertThat(repository.findById(reservation.id)?.customerDetails?.name).isEqualTo("John Doe")
    }

    @Test
    fun `delete a reservation`() {
        val reservation = Reservation.create(
            date = LocalDateTime.now(),
            customerDetails = CustomerDetails(
                name = "John",
                email = "john@test.com",
                phoneNumber = "931111111"
            ),
            partySize = 4
        )
        repository.insert(reservation)

        repository.delete(reservation.id)

        assertThat(repository.findById(reservation.id)).isNull()
    }
}