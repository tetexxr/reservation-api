package com.reservation.api.infrastructure.repositories

import com.reservation.api.domain.tables.Table
import com.reservation.api.domain.tables.TableNumber
import com.reservation.api.domain.tables.TableRepository

class TableInMemoryRepository : TableRepository {

    override fun findAll(): List<Table> {
        return tables.toList()
    }

    companion object {
        private val tables = listOf(
            Table(number = TableNumber(1), maximumSeatingCapacity = 2),
            Table(number = TableNumber(2), maximumSeatingCapacity = 2),
            Table(number = TableNumber(3), maximumSeatingCapacity = 4),
            Table(number = TableNumber(4), maximumSeatingCapacity = 4),
            Table(number = TableNumber(5), maximumSeatingCapacity = 6),
            Table(number = TableNumber(6), maximumSeatingCapacity = 6),
            Table(number = TableNumber(7), maximumSeatingCapacity = 8),
            Table(number = TableNumber(8), maximumSeatingCapacity = 10)
        )
    }
}