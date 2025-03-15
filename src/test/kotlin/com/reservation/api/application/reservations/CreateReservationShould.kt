package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class CreateReservationShould {

    @Test
    fun `create a reservation`() {
        val reservation = Reservation.create(
            time = LocalDateTime.now(),
            customerDetails = CustomerDetails(
                name = "John",
                email = "john@test.com",
                phoneNumber = "931111111"
            ),
            partySize = 4
        )
        val reservationRepository = mock<ReservationRepository> {
            on { insert(reservation) }.thenReturn(reservation)
        }
        val createReservation = CreateReservation(reservationRepository)
        val command = CreateReservationCommand(reservation)

        val id = createReservation.execute(command)

        verify(reservationRepository).insert(reservation)
        assertThat(id.value).isNotEmpty()
    }
}