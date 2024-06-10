package com.spring.dongnae.socket.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.dongnae.socket.scheme.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	Optional<ChatRoom> findById(String id);
	Optional<ChatRoom> findByUserIdsContaining(String userId);
	Optional<ChatRoom> findByUserIdsContaining(List<String> userIds);
}
