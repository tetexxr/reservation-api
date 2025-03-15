package com.reservation.api.infrastructure.controllers.reservations

import com.reservation.api.application.reservations.CreateReservation
import com.reservation.api.application.reservations.DeleteReservation
import com.reservation.api.application.reservations.DeleteReservationCommand
import com.reservation.api.application.reservations.UpdateReservation
import com.reservation.api.domain.reservations.ReservationId
import com.reservation.api.domain.reservations.ReservationRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/reservations")
class ReservationController(
    val createReservation: CreateReservation,
    val updateReservation: UpdateReservation,
    val deleteReservation: DeleteReservation,
    val reservationRepository: ReservationRepository
) {
    @PostMapping
    fun create(@RequestBody request: CreateReservationRequest): ResponseEntity<CreateReservationResponse> {
        val id = createReservation.execute(request.toCommand())
        return ResponseEntity<CreateReservationResponse>(CreateReservationResponse(id.value), HttpStatus.CREATED)
    }

    @PutMapping("/{reservationId}")
    fun update(@RequestBody request: UpdateReservationRequest, @PathVariable reservationId: String) {
        updateReservation.execute(request.toCommand(ReservationId(reservationId)))
    }

    @DeleteMapping("/{reservationId}")
    fun delete(@PathVariable reservationId: String): ResponseEntity<Any> {
        deleteReservation.execute(DeleteReservationCommand(ReservationId(reservationId)))
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun get(): GetReservationsResponse {
        val reservations = reservationRepository.findAll()
        return reservations.toDto()
    }
}