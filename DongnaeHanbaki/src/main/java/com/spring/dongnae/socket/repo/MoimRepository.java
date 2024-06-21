package com.spring.dongnae.socket.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.spring.dongnae.socket.scheme.Moim;

public interface MoimRepository extends MongoRepository<Moim, String> {
	Optional<Moim> findById(String id);
	boolean existsByName(String name);
	
    @Query("{ 'name': { $regex: ?0, $options: 'i' }, '_id': { $nin: ?1 } }")
    Page<Moim> findByNameContainingIgnoreCaseAndIdNotIn(String name, List<String> excludeIds, Pageable pageable);
}
