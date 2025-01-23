package com.krishna.notificationsystem.provider;

import com.krishna.notificationsystem.model.Notification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("smsNotificationProvider")
public class SMSNotificationProvider implements NotificationProvider {

    @Override
    public void send(Notification notification) {
        throw new RuntimeException("test");
     //   System.out.println("Sending SMS to user: " + notification.getUserId() + " with message: " + notification.getMessage());
    }
}
