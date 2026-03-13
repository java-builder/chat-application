package com.javabuilder.chatapp.mapper;

import com.javabuilder.chatapp.common.ConversationType;
import com.javabuilder.chatapp.dto.response.CreateConversationResponse;
import com.javabuilder.chatapp.dto.response.ParticipantResponse;
import com.javabuilder.chatapp.entity.Conversation;

public final class ConversationMapper {
    private ConversationMapper() {
    }

    public static CreateConversationResponse toConversationResponse(String creatorId, Conversation conversation) {
        ConversationType conversationType = conversation.getConversationType();

        CreateConversationResponse response = CreateConversationResponse.builder()
                .id(conversation.getId())
                .conversationType(conversationType)
                .participantInfo( conversation.getParticipants().stream()
                        .map(participants -> ParticipantResponse.builder()
                                .userId(participants.getUser().getId())
                                .username(participants.getUser().getUsername())
                                .build())
                        .toList())
                .createdAt(conversation.getCreatedAt())
                .build();

        if(conversationType == ConversationType.PRIVATE) {
            conversation.getParticipants()
                    .stream().filter(participants -> !participants.getUser().getId().equals(creatorId))
                    .findFirst().ifPresent(participantInfo -> response.setName(participantInfo.getUser().getUsername()));
        } else {
            response.setName(conversation.getName());
            response.setConversationAvatar(conversation.getConversationAvatar());
        }

        return response;
    }
}
