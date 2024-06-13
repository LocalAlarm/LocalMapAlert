package com.spring.dongnae.socket.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.dongnae.socket.scheme.FriendRoom;

public interface FriendRoomRepository extends MongoRepository<FriendRoom, String>{
	Optional<FriendRoom> findByToken(String token);
}
