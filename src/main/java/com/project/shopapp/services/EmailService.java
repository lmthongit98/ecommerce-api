package com.project.shopapp.services;

import com.project.shopapp.dtos.email.EmailRequestDto;

public interface EmailService {
    void sendEmail(EmailRequestDto emailRequestDto);
}
