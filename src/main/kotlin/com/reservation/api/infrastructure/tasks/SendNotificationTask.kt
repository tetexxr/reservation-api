package com.reservation.api.infrastructure.tasks

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SendNotificationTask {

    @Scheduled(cron = "0 * * * * ?")
    fun execute() {
        println("Checking to send notification every minute: ${LocalDateTime.now()}")
    }
}