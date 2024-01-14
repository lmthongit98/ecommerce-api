package com.project.shopapp.dtos.email;

import com.project.shopapp.enums.MessageType;

public interface MessageTemplate {
    MessageType getMessageType();
    String getTemplateName();
}
