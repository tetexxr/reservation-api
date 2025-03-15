package com.reservation.api.application.reservations

import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class UpdateReservationShould {

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
        val reservationRepository = mock<ReservationRepository>()
        val updateReservation = UpdateReservation(reservationRepository)
        val command = UpdateReservationCommand(reservation)

        updateReservation.execute(command)

        verify(reservationRepository).update(reservation)
    }
}