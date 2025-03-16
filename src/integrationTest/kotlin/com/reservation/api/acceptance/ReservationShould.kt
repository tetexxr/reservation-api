package com.reservation.api.acceptance

import com.reservation.api.Application
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.helpers.Cleaner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class ReservationShould {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    @Autowired
    private lateinit var cleaner: Cleaner

    @BeforeEach
    fun setUp() {
        cleaner.execute()
    }

    @Test
    fun `create a reservation`() {
        mvc
            .perform(
                post("/v1/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {
                            "time": "2021-10-10T10:00:00",
                            "name": "John",
                            "email": "john@test.com",
                            "phoneNumber": "931111111",
                            "partySize": 4
                        }
                        """
                    )
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.reservationId").isNotEmpty)
    }

    @Test
    fun `update a reservation`() {
        reservationRepository.insert(
            Reservation(
                id = ReservationId("reservation-id-1"),
                time = LocalDateTime.parse("2021-10-10T10:00:00"),
                customerDetails = CustomerDetails(
                    name = "John",
                    email = "john@test.com",
                    phoneNumber = "931111111"
                ),
                partySize = 4
            )
        )

        mvc
            .perform(
                put("/v1/reservations/{reservationId}", "reservation-id-1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {
                            "time": "2021-10-10T11:00:00",
                            "name": "John Doe",
                            "email": "john@test.com",
                            "phoneNumber": "931111111",
                            "partySize": 6
                        }
                        """
                    )
            )
            .andExpect(status().isOk())
    }

    @Test
    fun `delete a reservation`() {
        reservationRepository.insert(
            Reservation(
                id = ReservationId("reservation-id-2"),
                time = LocalDateTime.parse("2021-10-10T10:00:00"),
                customerDetails = CustomerDetails(
                    name = "John",
                    email = "john@test.com",
                    phoneNumber = "931111111"
                ),
                partySize = 4
            )
        )

        mvc
            .perform(
                delete("/v1/reservations/{reservationId}", "reservation-id-2")
            )
            .andExpect(status().isNoContent())

        assertThat(reservationRepository.findById(ReservationId("some-reservation-id"))).isNull()
    }

    @Test
    fun `get all reservations`() {
        (1..5).forEach {
            reservationRepository.insert(
                Reservation.create(
                    time = LocalDateTime.parse("2021-10-10T10:00:00"),
                    customerDetails = CustomerDetails(
                        name = "John $it",
                        email = "john@test.com",
                        phoneNumber = "931111111"
                    ),
                    partySize = 4
                )
            )
        }

        mvc
            .perform(get("/v1/reservations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.size()").value(5))
            .andExpect(jsonPath("$.total").value(5))
            .andExpect(jsonPath("$.items[0].name").value("John 1"))
            .andExpect(jsonPath("$.items[4].name").value("John 5"))
    }
}