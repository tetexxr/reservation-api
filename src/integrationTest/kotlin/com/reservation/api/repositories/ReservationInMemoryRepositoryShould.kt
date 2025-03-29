package com.reservation.api.repositories

import com.reservation.api.Application
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.helpers.Cleaner
import com.reservation.api.infrastructure.repositories.ReservationInMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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

    @Autowired
    private lateinit var cleaner: Cleaner

    @BeforeEach
    fun setUp() {
        cleaner.execute()
    }

    @Test
    fun `insert a reservation`() {
        val reservation = Reservation.create(
            time = LocalDateTime.now(),
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
            time = LocalDateTime.now(),
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
            time = LocalDateTime.now(),
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

    @Test
    fun `retrieve all reservations`() {
        (1..5).forEach {
            repository.insert(
                Reservation.create(
                    time = LocalDateTime.parse("2021-10-1${it}T10:00:00"),
                    customerDetails = CustomerDetails(
                        name = "John $it",
                        email = "john-$it@test.com",
                        phoneNumber = "931111111"
                    ),
                    partySize = 4
                )
            )
        }

        val reservations = repository.findAll()

        assertThat(reservations.size).isEqualTo(5)
        assertThat(reservations[0].customerDetails.name).isEqualTo("John 1")
        assertThat(reservations[2].time).isEqualTo(LocalDateTime.parse("2021-10-13T10:00:00"))
        assertThat(reservations[4].customerDetails.email).isEqualTo("john-5@test.com")
    }

    @Test
    fun `retrieve all reservations by name`() {
        insertReservation(0)
        insertReservation(0)
        insertReservation(1)

        val reservations = repository.findAll(name = "John 0")

        assertThat(reservations.size).isEqualTo(2)
        assertThat(reservations[0].customerDetails.name).isEqualTo("John 0")
        assertThat(reservations[1].customerDetails.name).isEqualTo("John 0")
    }

    private fun insertReservation(numberToAdd: Int) {
        repository.insert(
            Reservation.create(
                time = LocalDateTime.parse("2021-10-1${numberToAdd}T10:00:00"),
                customerDetails = CustomerDetails(
                    name = "John $numberToAdd",
                    email = "john-$numberToAdd@test.com",
                    phoneNumber = "931111111"
                ),
                partySize = 4
            )
        )
    }

    @Test
    fun `retrieve all reservations by letters in order of appearance`() {
        repository.insert(
            Reservation.create(
                time = LocalDateTime.parse("2021-10-10T10:00:00"),
                customerDetails = CustomerDetails(
                    name = "John Doe",
                    email = "john@test.com",
                    phoneNumber = "931111111"
                ),
                partySize = 4
            )
        )

        val reservations = repository.findAll(name = "jho")

        assertThat(reservations.size).isEqualTo(1)
        assertThat(reservations[0].customerDetails.name).isEqualTo("John Doe")
    }

    @Test
    fun `not retrieve all reservations by letters in order of appearance when not found`() {
        repository.insert(
            Reservation.create(
                time = LocalDateTime.parse("2021-10-10T10:00:00"),
                customerDetails = CustomerDetails(
                    name = "John Doe",
                    email = "john@test.com",
                    phoneNumber = "931111111"
                ),
                partySize = 4
            )
        )

        val reservations = repository.findAll(name = "jooo")

        assertThat(reservations.size).isEqualTo(0)
    }
}