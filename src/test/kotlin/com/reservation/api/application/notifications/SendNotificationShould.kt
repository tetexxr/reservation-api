package com.reservation.api.application.notifications

import com.reservation.api.domain.notifications.NotificationRepository
import com.reservation.api.domain.reservations.CustomerDetails
import com.reservation.api.domain.reservations.Reservation
import com.reservation.api.domain.reservations.ReservationRepository
import com.reservation.api.domain.reservations.ReservationTableRepository
import com.reservation.api.domain.tables.TableNumber
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class SendNotificationShould {

    private val reservationRepository = mock<ReservationRepository>()
    private val reservationTableRepository = mock<ReservationTableRepository>()
    private val notificationRepository = mock<NotificationRepository>()
    private val sendNotification = SendNotification(
        reservationRepository,
        reservationTableRepository,
        notificationRepository
    )

    @Test
    fun `send notification for reservations happening in one hour`() {
        val reservations = listOf(
            Reservation.create(
                LocalDateTime.now().plusHours(1),
                CustomerDetails("John", "john@test.com", "931111111"),
                4
            ),
            Reservation.create(
                LocalDateTime.now().plusHours(1).plusMinutes(1),
                CustomerDetails("Jane", "jane@test.com", "932222222"),
                2
            )
        )
        whenever(reservationRepository.findAll()).thenReturn(reservations)
        whenever(reservationTableRepository.findById(reservations[0].id)).thenReturn(TableNumber(1))

        sendNotification.execute()

        verify(notificationRepository).notify(reservations[0])
        verifyNoMoreInteractions(notificationRepository)
    }

    @Test
    fun `not send notification for reservations not happening in one hour`() {
        val reservationTime = LocalDateTime.now().plusHours(1).plusMinutes(1)
        val reservations = listOf(
            Reservation.create(
                reservationTime,
                CustomerDetails("John", "john@test.com", "931111111"),
                4
            )
        )
        whenever(reservationRepository.findAll()).thenReturn(reservations)

        sendNotification.execute()

        verifyNoInteractions(notificationRepository)
    }

    @Test
    fun `not send notification if the reservation do not have a table assigned`() {
        val reservationTime = LocalDateTime.now().plusHours(1)
        val reservations = listOf(
            Reservation.create(
                reservationTime,
                CustomerDetails("John", "john@test.com", "931111111"),
                4
            )
        )
        whenever(reservationRepository.findAll()).thenReturn(reservations)
        whenever(reservationTableRepository.findById(reservations[0].id)).thenReturn(null)

        sendNotification.execute()

        verifyNoInteractions(notificationRepository)
    }

    @Test
    fun `send notifications for multiple reservations happening in one hour`() {
        val reservationTime = LocalDateTime.now().plusHours(1)
        val reservations = listOf(
            Reservation.create(
                reservationTime,
                CustomerDetails("John", "john@test.com", "931111111"),
                4
            ),
            Reservation.create(
                reservationTime,
                CustomerDetails("Jane", "jane@test.com", "932222222"),
                2
            ),
            Reservation.create(
                LocalDateTime.now().plusHours(1).minusMinutes(1),
                CustomerDetails("Alice", "alice@test.com", "933333333"),
                6
            )
        )
        whenever(reservationRepository.findAll()).thenReturn(reservations)
        whenever(reservationTableRepository.findById(reservations[0].id)).thenReturn(TableNumber(1))
        whenever(reservationTableRepository.findById(reservations[1].id)).thenReturn(TableNumber(2))

        sendNotification.execute()

        verify(notificationRepository).notify(reservations[0])
        verify(notificationRepository).notify(reservations[1])
        verifyNoMoreInteractions(notificationRepository)
    }
}