package com.spring.dongnae.socket.scheme;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Optional<ChatMessage> findByUserId(String userId);
    //Optional<ChatMessage> findByToken(String token);
}