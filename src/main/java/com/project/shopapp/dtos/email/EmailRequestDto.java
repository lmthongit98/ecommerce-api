package com.project.shopapp.dtos.email;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto implements NotificationRequest {
    private String subject;
    private EmailId from;
    private List<EmailId> tos;
    private List<EmailId> ccs;
    private EmailTemplate emailTemplate;
    @Override
    public MessageTemplate getTemplate() {
        return emailTemplate;
    }
}
