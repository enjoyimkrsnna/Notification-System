package com.krishna.notificationsystem.service;

import com.krishna.notificationsystem.exception.NotificationException;
import com.krishna.notificationsystem.model.Notification;
import com.krishna.notificationsystem.model.NotificationPriority;
import com.krishna.notificationsystem.model.NotificationStatus;
import com.krishna.notificationsystem.util.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.krishna.notificationsystem.model.NotificationType.*;

@Service
public class NotificationProducer {
    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    private static final String HIGH_PRIORITY_TOPIC = "notification-topic-high";
    private static final String MEDIUM_PRIORITY_TOPIC = "notification-topic-medium";
    private static final String LOW_PRIORITY_TOPIC = "notification-topic-low";

    public void produceNotification(Notification notification) {
        if (notification.getMessage() == null || notification.getMessage().isEmpty()) {
            throw new NotificationException("Notification content cannot be empty.");
        }
        notification.setStatus(NotificationStatus.PENDING);
        notification.setTimestamp(NotificationUtils.getCurrentDateTime());

        switch (notification.getNotificationType()) {
            case OTP:
                notification.setPriority(NotificationPriority.HIGH);
                kafkaTemplate.send(HIGH_PRIORITY_TOPIC, notification);
                break;
            case TRANSACTIONAL:
                notification.setPriority(NotificationPriority.MEDIUM);
                kafkaTemplate.send(MEDIUM_PRIORITY_TOPIC, notification);
                break;
            case PROMOTIONAL, REMINDER, SYSTEM:
                notification.setPriority(NotificationPriority.LOW);
                kafkaTemplate.send(LOW_PRIORITY_TOPIC, notification);
                break;

            default:
                throw new NotificationException("Unknown notification type provided.");
        }
    }

}
