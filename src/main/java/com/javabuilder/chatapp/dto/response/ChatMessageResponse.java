package com.javabuilder.chatapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javabuilder.chatapp.common.MessageType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse implements Serializable {
    private String id;
    private String tempId;
    private String conversationId;
    private String conversationAvatar;
    private String senderId;
    private String senderName;
    private String content;
    private MessageType messageType;
    private List<MessageMediaResponse> messageMedia;
    private LocalDateTime createdAt;
}
