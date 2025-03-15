package com.reservation.api.domain.tables

interface TableRepository {
    fun findAll(): List<Table>
}