package com.project.shopapp.services;

import com.project.shopapp.dtos.email.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest request);
}
