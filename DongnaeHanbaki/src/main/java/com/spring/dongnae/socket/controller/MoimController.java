package com.spring.dongnae.socket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dongnae.socket.dto.MoimDto;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.service.MoimService;

@RestController
@RequestMapping("/moim")
public class MoimController {
	@Autowired
	private MoimService moimService;
	
    @PostMapping("/createMoim")
    public ResponseEntity<String> createMoim(
            @RequestParam("title") String title,
            @RequestParam("introduce") String introduce,
            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic) {
        try {
        	System.out.println("연결된다");
            moimService.createMoim(title, introduce, profilePic);
            return ResponseEntity.ok("모임이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모임 생성에 실패했습니다: " + e.getMessage());
        }
    }
    
    @GetMapping(value = "/{moimId}", produces = "application/json; charset=UTF-8")
    public MoimDto getMoimDtoInfo(@PathVariable String moimId) throws Exception {
    	System.out.println(moimId);
        return moimService.getMoimDtoInfo(moimId);
    }
	
	@PostMapping("/{moimId}/add-participants/{token}")
	public Moim addParticipantToMoim(@PathVariable String moimId, @PathVariable String token) {
		return moimService.addParticipantToMoim(moimId, token);
	}
	
    @PostMapping("/{moimId}/board")
    public Board addPostToGroup(@PathVariable String moimId, @RequestBody Board board) {
        return moimService.addBoardToMoim(moimId, board);
    }
    
    @DeleteMapping("/{boardId}")
    public boolean deleteBoard(@PathVariable String boardId) {
    	return moimService.deleteBoard(boardId);
    }
    
    @PostMapping("/{boardId}/likes")
    public boolean toggleLikeToPost(@PathVariable String boardId, @RequestParam String userEmail) {
        return moimService.toggleLikeToBoard(boardId, userEmail);
    }
    
    @PostMapping("/{boardId}/comments")
    public void addCommentToBoard(@PathVariable String baordId, @RequestBody Comment comment) {
    	moimService.addCommentToBoard(baordId, comment);
    }
    
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public boolean deleteCommentFromBoard(@PathVariable String boardId, @PathVariable String commentId) {
    	return moimService.deleteCommentFromBoard(boardId, commentId);
    }
    
    @GetMapping("/{moimId}/all-boards")
    public List<Board> getBoardsByMoimId(@PathVariable String moimId) {
    	return moimService.getBoardByMoimId(moimId);
    }

    @GetMapping("/{moimId}/boards")
    public Page<Board> getBoards(@PathVariable String moimId,
                                 @RequestParam int page,
                                 @RequestParam int size) {
    	Pageable pageable = new PageRequest(page, size); // 수정된 부분
        return moimService.getBoardsByMoimIdPaged(moimId, pageable);
    }
    
//    @GetMapping("/userRooms/{token}")
//    public List<Moim> getMoimsByUser(@PathVariable String token) {
//    	return moimService.getMoimByToken(token);
//    }
	
}
