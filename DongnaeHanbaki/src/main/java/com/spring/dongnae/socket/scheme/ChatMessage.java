package com.spring.dongnae.socket.scheme;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatMessage {

    @Id
    private String id;
    private String userId;
    private String message;

    public ChatMessage(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    // Getters and setters
}