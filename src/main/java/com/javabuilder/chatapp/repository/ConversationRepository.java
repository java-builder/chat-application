package com.javabuilder.chatapp.repository;

import com.javabuilder.chatapp.entity.Conversation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

    @EntityGraph(attributePaths = {"participants", "participants.user"})
    Optional<Conversation> findByParticipantHash(String participantHash);
}
