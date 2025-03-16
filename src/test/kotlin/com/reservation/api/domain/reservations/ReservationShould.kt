package com.reservation.api.domain.reservations

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationShould {

    @Test
    fun `overlap with begin time`() {
        val time = LocalDateTime.parse("2021-10-10T09:30:00")
        assertThat(reservation.isOverlappingWith(time)).isTrue()
    }

    @Test
    fun `overlap with end time`() {
        val time = LocalDateTime.parse("2021-10-10T10:30:00")
        assertThat(reservation.isOverlappingWith(time)).isTrue()
    }

    @Test
    fun `not overlap and are earlier`() {
        val time = LocalDateTime.parse("2021-10-10T09:15:00")
        assertThat(reservation.isOverlappingWith(time)).isFalse()
    }

    @Test
    fun `not overlap and are later`() {
        val time = LocalDateTime.parse("2021-10-10T10:45:00")
        assertThat(reservation.isOverlappingWith(time)).isFalse()
    }

    companion object {
        private val reservation = Reservation.create(
            time = LocalDateTime.parse("2021-10-10T10:00:00"),
            customerDetails = CustomerDetails("John", "john@test.com", "931111111"),
            partySize = 4
        )
    }
}