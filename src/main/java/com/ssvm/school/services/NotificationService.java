package com.ssvm.school.services;

import com.ssvm.school.constants.NotificationType;
import com.ssvm.school.event.NotificationEvent;

public interface NotificationService {
    void send(NotificationEvent event);

    NotificationType getSupportedType();
}
