package com.krishna.notificationsystem.provider;

import com.krishna.notificationsystem.model.Notification;

public interface NotificationProvider {
    void send(Notification notification);
}
