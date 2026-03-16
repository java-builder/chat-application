package com.javabuilder.chatapp.entity;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPresence {
    private String userId;
    private Instant lastOnlineAt;
}
