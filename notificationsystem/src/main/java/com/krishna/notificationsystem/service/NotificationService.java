package com.krishna.notificationsystem.service;

import com.krishna.notificationsystem.exception.NotificationException;
import com.krishna.notificationsystem.model.Notification;
import com.krishna.notificationsystem.model.NotificationStatus;
import com.krishna.notificationsystem.provider.NotificationProvider;
import com.krishna.notificationsystem.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.krishna.notificationsystem.model.NotificationChannel.*;
import static com.krishna.notificationsystem.model.NotificationChannel.PUSH;


@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    @Qualifier("emailNotificationProvider")
    private NotificationProvider emailProvider;

    @Autowired
    @Qualifier("smsNotificationProvider")
    private NotificationProvider smsProvider;

    @Autowired
    @Qualifier("pushNotificationProvider")
    private NotificationProvider pushProvider;

    @Autowired
    private NotificationRepository notificationRepository;

    public void processNotificationsInBatch(List<Notification> notifications) {
        notifications.forEach(notification -> {
            try {
                switch (notification.getNotificationChannel()) {
                    case EMAIL:
                        emailProvider.send(notification);
                        break;
                    case SMS:
                        smsProvider.send(notification);
                        break;
                    case PUSH:
                        pushProvider.send(notification);
                        break;
                    default:
                        throw new NotificationException("Invalid notification Channel.");
                }
                notification.setStatus(NotificationStatus.SENT);
            } catch (Exception e) {
                notification.setStatus(NotificationStatus.FAILED);
                logger.error("Failed to process notification for user {}: {}", notification.getUserId(), e.getMessage());
            }
        });
        // Save all notifications to the database in a single batch
        notificationRepository.saveAll(notifications);
        logger.info("Processed and saved batch of {} notifications", notifications.size());
    }
}

