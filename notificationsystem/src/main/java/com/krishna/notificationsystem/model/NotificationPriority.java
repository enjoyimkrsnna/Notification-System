package com.krishna.notificationsystem.model;

public enum NotificationPriority {
    HIGH,    // For OTP, transactional
    MEDIUM,  // For less critical transactional notifications
    LOW      // For promotional messages
}
