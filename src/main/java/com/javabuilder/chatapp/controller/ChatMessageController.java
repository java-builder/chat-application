package com.javabuilder.chatapp.controller;

import com.javabuilder.chatapp.dto.request.ChatMessageRequest;
import com.javabuilder.chatapp.dto.response.ApiResponse;
import com.javabuilder.chatapp.dto.response.ChatMessageResponse;
import com.javabuilder.chatapp.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping
    ApiResponse<ChatMessageResponse> sendChatMessage(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid ChatMessageRequest request) {
        var senderId = jwt.getSubject();
        var data = chatMessageService.sendChatMessage(senderId, request);

        return ApiResponse.<ChatMessageResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Chat message sent successfully")
                .data(data)
                .build();
    }

}
