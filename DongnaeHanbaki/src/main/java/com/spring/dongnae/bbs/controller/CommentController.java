package com.spring.dongnae.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.bbs.CommentService;
import com.spring.dongnae.bbs.CommentVO;

@RestController
public class CommentController {
	
	private final CommentService commentService;
	
	@Autowired
	public CommentController (CommentService commentService) {
		this.commentService = commentService;
		System.out.println("====== CommentController() 객체 생성 ======");
	}
	
	@PostMapping(value = "/insertComment", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> insertComment(@ModelAttribute CommentVO commentVO) {
	    try {
	        int insertCount = commentService.insertComment(commentVO);
	        if (insertCount > 0) {
	            return ResponseEntity.ok("댓글이 성공적으로 등록되었습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록에 실패했습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록 중 오류가 발생했습니다.");
	    }
	}

}
