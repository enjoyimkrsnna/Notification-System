package com.krishna.notificationsystem.controller;

import com.krishna.notificationsystem.exception.NotificationException;
import com.krishna.notificationsystem.model.Notification;
import com.krishna.notificationsystem.service.NotificationProducer;
import com.krishna.notificationsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationProducer notificationProducer;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        try {
            notificationProducer.produceNotification(notification);
            return new ResponseEntity<>("Notification is being processed!", HttpStatus.ACCEPTED);
        } catch (NotificationException ex) {
           throw ex;
        } catch (Exception ex) {
            return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestBody Notification notification) {
        try {
            System.out.println("testing...");
            return new ResponseEntity<>("testig....!", HttpStatus.ACCEPTED);
        } catch (NotificationException ex) {
            throw ex;
        } catch (Exception ex) {
            return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
