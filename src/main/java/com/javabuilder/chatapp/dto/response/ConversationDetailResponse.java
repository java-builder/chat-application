package com.javabuilder.chatapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javabuilder.chatapp.common.ConversationType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationDetailResponse {
    private String id;
    private ConversationType conversationType;
    private String name;
    private String conversationAvatar;
    private List<ParticipantResponse> participantInfo;
    private String lastMessageId;
    private String lastMessageContent;
    private LocalDateTime lastMessageTime;
    private LocalDateTime createdAt;
}
