package com.javabuilder.chatapp.mapper;

import com.javabuilder.chatapp.common.ConversationType;
import com.javabuilder.chatapp.dto.response.ConversationDetailResponse;
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
                .participantInfo(conversation.getParticipants().stream()
                        .map(participants -> ParticipantResponse.builder()
                                .userId(participants.getUser().getId())
                                .username(participants.getUser().getUsername())
                                .build())
                        .toList())
                .createdAt(conversation.getCreatedAt())
                .build();

        String name = resolveConversationName(creatorId, conversation);
        response.setName(name);

        if (conversation.getConversationType() != ConversationType.PRIVATE) {
            response.setConversationAvatar(conversation.getConversationAvatar());
        }

        return response;
    }

    public static ConversationDetailResponse toConversationDetailResponse(String creatorId, Conversation conversation) {
        ConversationType conversationType = conversation.getConversationType();

        ConversationDetailResponse response = ConversationDetailResponse.builder()
                .id(conversation.getId())
                .conversationType(conversationType)
                .participantInfo( conversation.getParticipants().stream()
                        .map(participants -> ParticipantResponse.builder()
                                .userId(participants.getUser().getId())
                                .username(participants.getUser().getUsername())
                                .build())
                        .toList())
                .lastMessageId(conversation.getLastMessageId())
                .lastMessageContent(conversation.getLastMessageContent())
                .lastMessageTime(conversation.getLastMessageTime())
                .createdAt(conversation.getCreatedAt())
                .build();

        String name = resolveConversationName(creatorId, conversation);
        response.setName(name);

        if (conversation.getConversationType() != ConversationType.PRIVATE) {
            response.setConversationAvatar(conversation.getConversationAvatar());
        }

        return response;
    }

    private static String resolveConversationName(String creatorId, Conversation conversation) {
        if (conversation.getConversationType() == ConversationType.PRIVATE) {
            return conversation.getParticipants()
                    .stream()
                    .filter(p -> !p.getUser().getId().equals(creatorId))
                    .findFirst()
                    .map(p -> p.getUser().getUsername())
                    .orElse(null);
        }
        return conversation.getName();
    }

}
