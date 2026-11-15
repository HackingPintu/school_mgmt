package com.ssvm.school.event;

import com.ssvm.school.constants.NotificationType;
import java.util.Map;

public record NotificationEvent(
        String recipient,
        String subject,
        String message,
        NotificationType type,
        String templateName,
        Map<String, Object> templateModel,
        String attachmentName
) {
}
