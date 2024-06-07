package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/* 
@Document(collection = "chatMessages") : 데이터베이스에 저장위치를 말함  
 */

@Document(collection = "chatMessages")
public class ChatMessage {

    @Id
    private String id;
    private String userId;
    private List<HashMap<String, String>> messages = new ArrayList<>();

    // Constructors, getters, and setters

    public ChatMessage() {}

    public ChatMessage(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<HashMap<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<HashMap<String, String>> messages) {
        this.messages = messages;
    }

    public void addMessage(HashMap<String, String> message) {
        this.messages.add(message);
    }
}