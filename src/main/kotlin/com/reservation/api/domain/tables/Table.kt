package com.reservation.api.domain.tables

data class Table(
    val number: TableNumber,
    val maximumSeatingCapacity: Int
) {
    fun isSuitableForPartySize(partySize: Int): Boolean {
        return (isOdd(partySize) && partySize + 1 == maximumSeatingCapacity) ||
                (!isOdd(partySize) && partySize == maximumSeatingCapacity)
    }

    private fun isOdd(partySize: Int): Boolean {
        return partySize % 2 != 0
    }
}