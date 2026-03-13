package com.javabuilder.chatapp.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MessageMediaResponse(
        Long id,
        String fileName,
        String fileType,
        String thumbnailUrl,
        LocalDateTime uploadedAt
) {
}
