package com.himanshu.notificationinvitationservice.controller;

import com.himanshu.notificationinvitationservice.entity.Notification;
import com.himanshu.notificationinvitationservice.service.NotificationInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationInvitationService notificationInvitationService;

    @Autowired
    public NotificationController(NotificationInvitationService notificationInvitationService) {
        this.notificationInvitationService = notificationInvitationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationInvitationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<Notification>> getNotificationsByRecipientId(@PathVariable Long recipientId) {
        List<Notification> notifications = notificationInvitationService.getNotificationsByRecipientId(recipientId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationInvitationService.markNotificationAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }
}
