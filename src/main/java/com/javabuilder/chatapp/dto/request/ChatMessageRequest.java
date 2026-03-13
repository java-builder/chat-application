package com.javabuilder.chatapp.dto.request;

import com.javabuilder.chatapp.common.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ChatMessageRequest(

        String tempId,

        @NotBlank(message = "Conversation id is required")
        String conversationId,

        String content,

        @NotNull(message = "Message type is required")
        MessageType messageType,

        List<MessageMediaRequest> messageMedia
) {
}
