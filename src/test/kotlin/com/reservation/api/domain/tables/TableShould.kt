package com.reservation.api.domain.tables

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TableShould {

    @Test
    fun `be suitable when party size is even and matches maximum seating capacity`() {
        val partySize = 4
        assertThat(table.isSuitableForPartySize(partySize)).isTrue()
    }

    @Test
    fun `be suitable when party size is odd and one less than maximum seating capacity`() {
        val partySize = 3
        assertThat(table.isSuitableForPartySize(partySize)).isTrue()
    }

    @Test
    fun `not be suitable when party size is even and does not match maximum seating capacity`() {
        val partySize = 6
        assertThat(table.isSuitableForPartySize(partySize)).isFalse()
    }

    @Test
    fun `not be suitable when party size is odd and not one less than maximum seating capacity`() {
        val partySize = 1
        assertThat(table.isSuitableForPartySize(partySize)).isFalse()
    }

    companion object {
        private val table = Table(
            number = TableNumber(1),
            maximumSeatingCapacity = 4
        )
    }
}