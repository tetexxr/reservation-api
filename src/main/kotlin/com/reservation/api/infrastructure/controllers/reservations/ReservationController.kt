package com.reservation.api.infrastructure.controllers.reservations

import com.reservation.api.application.reservations.CreateReservation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/reservations")
class ReservationController(
    val createReservation: CreateReservation
) {
    @PostMapping
    fun create(@RequestBody request: CreateReservationRequest): ResponseEntity<CreateReservationResponse> {
        val id = createReservation.execute(request.toCommand())
        return ResponseEntity<CreateReservationResponse>(CreateReservationResponse(id.value), HttpStatus.CREATED)
    }
}