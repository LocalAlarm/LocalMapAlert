package com.spring.dongnae.custom.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.dongnae.custom.scheme.CustomMarker;

@Repository
public interface CustomRepository extends MongoRepository<CustomMarker, String>{
	Optional<CustomMarker> findByMapIdx(int MapIdx);
}
