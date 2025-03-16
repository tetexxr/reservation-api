package com.reservation.api.infrastructure.controllers.availability

import com.reservation.api.application.availability.GetAvailableSlots
import com.reservation.api.application.availability.GetAvailableSlotsQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/v1/availability")
class AvailabilityController(
    private val getAvailableSlots: GetAvailableSlots
) {
    @GetMapping
    fun get(@RequestParam date: String, @RequestParam partySize: Int): GetAvailableSlotsResponse {
        val query = GetAvailableSlotsQuery(LocalDate.parse(date), partySize)
        val availableSlots = getAvailableSlots.execute(query)
        return availableSlots.toDto()
    }
}