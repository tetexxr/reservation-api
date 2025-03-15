package com.reservation.api.infrastructure.controllers.reservations

import com.reservation.api.application.reservations.CreateReservation
import com.reservation.api.application.reservations.UpdateReservation
import com.reservation.api.domain.reservations.ReservationId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    val updateReservation: UpdateReservation
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
}