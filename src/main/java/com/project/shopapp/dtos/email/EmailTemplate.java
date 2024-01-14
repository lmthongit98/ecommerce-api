package com.project.shopapp.dtos.email;

import com.project.shopapp.enums.MessageType;

public abstract class EmailTemplate implements MessageTemplate {
    @Override
    public MessageType getMessageType() {
        return MessageType.EMAIL;
    }
}
