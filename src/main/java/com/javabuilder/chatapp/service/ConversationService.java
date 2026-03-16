package com.javabuilder.chatapp.service;

import com.javabuilder.chatapp.common.ConversationType;
import com.javabuilder.chatapp.dto.request.CreateConversationRequest;
import com.javabuilder.chatapp.dto.response.ConversationDetailResponse;
import com.javabuilder.chatapp.dto.response.CreateConversationResponse;
import com.javabuilder.chatapp.dto.response.PageResponse;
import com.javabuilder.chatapp.entity.Conversation;
import com.javabuilder.chatapp.entity.User;
import com.javabuilder.chatapp.exception.AppException;
import com.javabuilder.chatapp.exception.ErrorCode;
import com.javabuilder.chatapp.mapper.ConversationMapper;
import com.javabuilder.chatapp.repository.ConversationRepository;
import com.javabuilder.chatapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;

    public CreateConversationResponse createConversation(String creatorId, CreateConversationRequest request) {
        List<String> participantIds = request.participantIds();

        if (!participantIds.contains(creatorId)) {
            participantIds.add(creatorId);
        }

        List<User> participantInfos = userRepository.findAllById(participantIds);

        if (participantInfos.size() != participantIds.size()) {
            throw new AppException(ErrorCode.PARTICIPANT_NOT_FOUND);
        }

        ConversationType conversationType = request.conversationType();
        String participantHash = null;

        if (conversationType == ConversationType.PRIVATE) {
            if (participantInfos.size() != 2)
                throw new AppException(ErrorCode.INVALID_PARTICIPANT_COUNT);

            participantHash = participantInfos.stream()
                    .map(User::getId)
                    .sorted()
                    .collect(Collectors.joining("_"));

            Optional<Conversation> existing = conversationRepository.findByParticipantHash(participantHash);
            if (existing.isPresent()) {
                return conversationMapper.toConversationResponse(creatorId, existing.get());
            }
        }

        if (conversationType == ConversationType.GROUP) {
            if (request.name() == null || request.name().trim().isEmpty())
                throw new AppException(ErrorCode.CONVERSATION_NAME_REQUIRED);

            if (participantIds.size() < 3)
                throw new AppException(ErrorCode.GROUP_CONVERSATION_MINIMUM_THREE_PARTICIPANTS);
        }

        Conversation conversation = Conversation.builder()
                .name(request.name())
                .conversationType(conversationType)
                .conversationAvatar(request.conversationAvatar())
                .participantHash(participantHash)
                .createdAt(LocalDateTime.now())
                .build();

        participantInfos.forEach(conversation::addParticipants);
        conversationRepository.save(conversation);

        return conversationMapper.toConversationResponse(creatorId, conversation);
    }

    public PageResponse<ConversationDetailResponse> getMyConversation(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Conversation> conversationPage = conversationRepository.findAllByUserId(userId, pageable);
        
        List<String> conversationIds = conversationPage.getContent().stream()
                .map(Conversation::getId)
                .toList();

        List<Conversation> conversationsWithParticipants = conversationIds.isEmpty() ?
                List.of() : conversationRepository.findByIdInWithParticipants(conversationIds);

        List<ConversationDetailResponse> responses = conversationsWithParticipants.stream()
                .map(conversation -> conversationMapper.toConversationDetailResponse(userId, conversation))
                .toList();

        return PageResponse.<ConversationDetailResponse>builder()
                .currentPage(page)
                .pageSize(pageable.getPageSize())
                .totalPages(conversationPage.getTotalPages())
                .totalElements(conversationPage.getTotalElements())
                .content(responses)
                .build();
    }

}
