package com.reservation.api.acceptance

import com.reservation.api.Application
import com.reservation.api.application.reservations.CreateReservation
import com.reservation.api.application.reservations.CreateReservationCommand
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class AvailabilityShould {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var createReservation: CreateReservation

    @Test
    fun `get empty when np available slots`() {
        val opening = LocalDateTime.parse("2021-10-10T08:00:00")
        (0..23).map {
            val time = opening.plusMinutes(45 * it.toLong())
            // Insert one reservation for each table
            createReservation.execute(CreateReservationCommand(reservationAt(time)))
            createReservation.execute(CreateReservationCommand(reservationAt(time)))
        }

        mvc
            .perform(
                get("/v1/availability")
                    .param("date", "2021-10-10")
                    .param("partySize", "4")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.size()").value(0))
            .andExpect(jsonPath("$.total").value(0))
    }

    companion object {
        private fun reservationAt(time: LocalDateTime) = Reservation.create(
            time = time,
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 4
        )
    }
}