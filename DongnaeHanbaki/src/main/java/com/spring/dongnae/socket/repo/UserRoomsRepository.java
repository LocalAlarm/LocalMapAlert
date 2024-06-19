package com.spring.dongnae.socket.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring.dongnae.socket.scheme.UserRooms;

public interface UserRoomsRepository extends MongoRepository<UserRooms, String> {
    Optional<UserRooms> findById(String id);
    
    @Query(value = "{ '_id': ?0 }", fields = "{ 'id': 1, 'email': 1, 'moims': 1, 'masterMoims': 1 }")
    Optional<UserRooms> findByIdWithSelectedFields(String id);
}