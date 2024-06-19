package com.spring.dongnae.socket.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.dongnae.socket.scheme.UserRooms;

public interface UserRoomsRepository extends MongoRepository<UserRooms, String> {
    Optional<UserRooms> findById(String id);
}
