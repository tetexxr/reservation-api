package com.reservation.api.repositories

import com.reservation.api.Application
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.tables.TableNumber
import com.reservation.api.helpers.Cleaner
import com.reservation.api.infrastructure.repositories.ReservationTableInMemoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [Application::class])
@ActiveProfiles("integration-test")
class ReservationTableInMemoryRepositoryShould {

    @Autowired
    private lateinit var repository: ReservationTableInMemoryRepository

    @Autowired
    private lateinit var cleaner: Cleaner

    @BeforeEach
    fun setUp() {
        cleaner.execute()
    }

    @Test
    fun `add a reservation table`() {
        val reservationId = ReservationId.new()
        val tableNumber = TableNumber(1)

        repository.add(reservationId, tableNumber)

        assertThat(repository.findById(reservationId)).isEqualTo(tableNumber)
    }

    @Test
    fun `remove a reservation table`() {
        val reservationId = ReservationId.new()
        val tableNumber = TableNumber(1)
        repository.add(reservationId, tableNumber)

        repository.remove(reservationId)

        assertThat(repository.findById(reservationId)).isNull()
    }

    @Test
    fun `retrieve all reservation tables`() {
        val reservationId1 = ReservationId.new()
        val reservationId2 = ReservationId.new()
        val tableNumber1 = TableNumber(1)
        val tableNumber2 = TableNumber(2)
        repository.add(reservationId1, tableNumber1)
        repository.add(reservationId2, tableNumber2)

        val reservationTables = repository.findAll()

        assertThat(reservationTables.size).isEqualTo(2)
        assertThat(reservationTables).isEqualTo(
            mapOf(
                reservationId1 to tableNumber1,
                reservationId2 to tableNumber2
            )
        )
    }

    @Test
    fun `find a reservation table by reservation id`() {
        val reservationId = ReservationId.new()
        val tableNumber = TableNumber(1)
        repository.add(reservationId, tableNumber)

        assertThat(repository.findById(reservationId)).isEqualTo(tableNumber)
    }
}