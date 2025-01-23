package com.krishna.notificationsystem.service;

import com.krishna.notificationsystem.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

//    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
//    public void consumeNotification(Notification notification) {
//        System.out.println("Consumed notification: " + notification.getMessage());
//        notificationService.processNotification(notification);
//    }

    @KafkaListener(topics = "notification-topic-high", groupId = "notification-group-high", containerFactory = "kafkaListenerContainerFactory")
    public void consumeHighPriorityNotifications(List<Notification> notifications) {
        System.out.println("Consumed HIGH priority notifications: " + notifications.size());
        notificationService.processNotificationsInBatch(notifications);
    }

    @KafkaListener(topics = "notification-topic-medium", groupId = "notification-group-medium", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMediumPriorityNotifications(List<Notification> notifications) {
        System.out.println("Consumed MEDIUM priority notifications: " + notifications.size());
        notificationService.processNotificationsInBatch(notifications);
    }

    @KafkaListener(topics = "notification-topic-low", groupId = "notification-group-low", containerFactory = "kafkaListenerContainerFactory")
    public void consumeLowPriorityNotifications(List<Notification> notifications) {
        System.out.println("Consumed LOW priority notifications: " + notifications.size());
        notificationService.processNotificationsInBatch(notifications);
    }
}
