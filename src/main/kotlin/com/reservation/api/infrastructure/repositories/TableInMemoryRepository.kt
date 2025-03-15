package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.availability.Table
import com.reservation.api.domain.availability.TableRepository

class TableInMemoryRepository : TableRepository {

    override fun findAll(): List<Table> {
        return tables.toList()
    }

    companion object {
        private val tables = listOf(
            Table(number = 1, maximumSeatingCapacity = 2),
            Table(number = 2, maximumSeatingCapacity = 2),
            Table(number = 3, maximumSeatingCapacity = 4),
            Table(number = 4, maximumSeatingCapacity = 4),
            Table(number = 5, maximumSeatingCapacity = 6),
            Table(number = 6, maximumSeatingCapacity = 6),
            Table(number = 7, maximumSeatingCapacity = 8),
            Table(number = 8, maximumSeatingCapacity = 10)
        )
    }
}