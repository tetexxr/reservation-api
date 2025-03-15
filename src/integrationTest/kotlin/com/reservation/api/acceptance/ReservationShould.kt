package com.reservation.api.acceptance

import com.reservation.api.Application
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class ReservationShould {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `create a reservation`() {
        mvc
            .perform(
                post("/v1/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {
                            "date": "2021-10-10T10:00:00",
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
        mvc
            .perform(
                put("/v1/reservations/{reservationId}", "some-reservation-id")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                        {
                            "date": "2021-10-10T11:00:00",
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
}