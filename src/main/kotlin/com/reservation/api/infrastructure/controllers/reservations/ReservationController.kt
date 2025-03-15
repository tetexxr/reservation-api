package com.reservation.api.infrastructure.controllers.reservations

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/reservations")
class ReservationController {

    @PostMapping
    fun create(@RequestBody request: CreateReservationRequest): ResponseEntity<CreateReservationResponse> {
        return ResponseEntity<CreateReservationResponse>(CreateReservationResponse(""), HttpStatus.CREATED)
    }
}