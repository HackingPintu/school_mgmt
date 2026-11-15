package com.ssvm.school.components;

import com.ssvm.school.constants.NotificationType;
import com.ssvm.school.services.NotificationService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class NotificationFactory {

    private final Map<NotificationType, NotificationService> services;

    public NotificationFactory(List<NotificationService> serviceList) {
        this.services = serviceList.stream()
                .collect(Collectors.toMap(NotificationService::getSupportedType, Function.identity()));
    }

    public NotificationService getService(NotificationType type) {
        NotificationService service = services.get(type);
        if (service == null) {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
        return service;
    }
}
