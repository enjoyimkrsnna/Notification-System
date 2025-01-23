package com.krishna.notificationsystem.provider;


import com.krishna.notificationsystem.model.Notification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("pushNotificationProvider")
public class PushNotificationProvider implements NotificationProvider {

    @Override
    public void send(Notification notification) {
        System.out.println("Sending push notification to user: " + notification.getUserId() + " with message: " + notification.getMessage());
    }
}

