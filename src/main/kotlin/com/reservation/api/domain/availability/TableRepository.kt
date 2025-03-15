package com.reservation.api.domain.availability

interface TableRepository {
    fun findAll(): List<Table>
}