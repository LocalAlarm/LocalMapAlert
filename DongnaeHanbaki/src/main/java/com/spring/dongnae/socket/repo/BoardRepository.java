package com.spring.dongnae.socket.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.dongnae.socket.scheme.Board;

public interface BoardRepository extends MongoRepository<Board, String> {
	List<Board> findByMoimId(String moimId);

	// 페이징 처리를 위한 메서드
	Page<Board> findByMoimId(String moimId, Pageable pageable);
	
	Optional<Board> findById(String boardId);
}
