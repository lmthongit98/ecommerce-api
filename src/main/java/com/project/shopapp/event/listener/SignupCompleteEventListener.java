package com.project.shopapp.event.listener;

import com.project.shopapp.dtos.email.EmailId;
import com.project.shopapp.dtos.email.EmailRequestDto;
import com.project.shopapp.dtos.email.EmailTemplate;
import com.project.shopapp.dtos.email.templates.SignUpConfirmEmailTemplate;
import com.project.shopapp.event.SignupCompleteEvent;
import com.project.shopapp.models.User;
import com.project.shopapp.services.NotificationService;
import com.project.shopapp.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignupCompleteEventListener implements ApplicationListener<SignupCompleteEvent> {

    private final NotificationService notificationService;
    private final UserService userService;

    @Override
    public void onApplicationEvent(@NonNull SignupCompleteEvent event) {
        log.info("Listen signup event {}", event);
        User user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(user, verificationToken);
        String url = event.getVerifyUrl() + verificationToken;
        EmailTemplate emailTemplate = new SignUpConfirmEmailTemplate(user.getFullName(), url);
        EmailRequestDto emailRequestDto = EmailRequestDto.builder()
                .subject("Signup Confirmation")
                .tos(Collections.singletonList(new EmailId(user.getFullName(), user.getEmail())))
                .emailTemplate(emailTemplate)
                .build();
        notificationService.sendNotification(emailRequestDto);
    }

}
