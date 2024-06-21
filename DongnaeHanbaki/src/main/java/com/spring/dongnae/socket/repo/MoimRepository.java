package com.spring.dongnae.socket.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.dongnae.socket.scheme.Moim;

public interface MoimRepository extends MongoRepository<Moim, String> {
	Optional<Moim> findById(String id);
}
