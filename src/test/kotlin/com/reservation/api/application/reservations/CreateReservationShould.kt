package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.time.LocalDateTime

class CreateReservationShould {

    @Test
    fun `create a reservation`() {
        val reservationRepository = mock<ReservationRepository>()
        val createReservation = CreateReservation(reservationRepository)
        val reservation = Reservation.create(
            date = LocalDateTime.now(),
            customerDetails = CustomerDetails(
                name = "John",
                email = "john@test.com",
                phoneNumber = "931111111"
            ),
            partySize = 4
        )
        val command = CreateReservationCommand(reservation)

        createReservation.execute(command)

        verify(reservationRepository).create(reservation)
    }
}