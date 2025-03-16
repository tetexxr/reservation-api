package com.reservation.api.infrastructure.tasks

import com.reservation.api.application.notifications.SendNotification
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SendNotificationTask(
    private val sendNotification: SendNotification
) {
    @Scheduled(cron = "0 * * * * ?")
    fun execute() {
        sendNotification.execute()
    }
}