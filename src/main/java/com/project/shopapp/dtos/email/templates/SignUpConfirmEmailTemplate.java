package com.project.shopapp.dtos.email.templates;

import com.project.shopapp.dtos.email.EmailTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpConfirmEmailTemplate extends EmailTemplate {

    private String recipientName;

    private String confirmationUrl;

    @Override
    public String getTemplateName() {
        return "confirmation-email";
    }
}
