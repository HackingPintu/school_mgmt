package com.ssvm.school.event;

import com.ssvm.school.components.NotificationFactory;
import com.ssvm.school.services.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class NotificationEventListener {

    private final NotificationFactory notificationFactory;

    public NotificationEventListener(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    @Async
    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        try {
            NotificationService channel = notificationFactory.getService(event.type());
            channel.send(event);
        } catch (Exception e) {
            System.err.println("Failed to process notification: " + e.getMessage());
        }
    }
}
