package com.spring.dongnae.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.socket.repo.BoardRepository;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;
@Service
public class BoardService {
	@Autowired
	private ImageUploadController imageUploadController;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private GetAuthenticInfo getAuthenticInfo;
	
	public long countBoardsByMoimId(String moimId) {
		return boardRepository.countByMoimId(moimId);
	}
}
