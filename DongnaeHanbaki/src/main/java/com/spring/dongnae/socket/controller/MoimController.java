package com.spring.dongnae.socket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.socket.dto.MoimDto;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Image;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.service.MoimService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController
@RequestMapping("/moim")
public class MoimController {
	@Autowired
	private MoimService moimService;
	@Autowired
	private GetAuthenticInfo getAuthenticInfo;
	@Autowired
	private ImageUploadController imageUploadController;
	
    @PostMapping("/createMoim")
    public ResponseEntity<String> createMoim(
            @RequestParam("title") String title,
            @RequestParam("introduce") String introduce,
            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic) {
        try {
            moimService.createMoim(title, introduce, profilePic);
            return ResponseEntity.ok("모임이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모임 생성에 실패했습니다: " + e.getMessage());
        }
    }
    
    @GetMapping(value = "/{moimId}", produces = "application/json; charset=UTF-8")
    public MoimDto getMoimDtoInfo(@PathVariable String moimId) throws Exception {
        return moimService.getMoimDtoInfo(moimId);
    }
	
    @PostMapping("/{moimId}/add-participants/{token}")
    public Moim addParticipantToMoim(@PathVariable String moimId, @PathVariable String token) {
      return moimService.addParticipantToMoim(moimId, token);
    }

    
    @PostMapping("/{moimId}/board")
    public Board addPostToMoim(@PathVariable String moimId, 
                               @RequestParam("title") String title, 
                               @RequestParam("content") String content, 
                               @RequestParam("images") MultipartFile[] images) {
        // Board 객체 생성
        Board board = new Board(moimId, title, content, getAuthenticInfo.GetToken());
        // 이미지 파일 처리 (예: 저장 경로 지정, 파일 저장 등)
        for (MultipartFile imageFile : images) {
        	if (!imageFile.isEmpty()) {
        		Map<String, String> imageMap = imageUploadController.uploadImage(imageFile);
        		Image image = new Image();
        		image.setImage(imageMap.get("url"));
        		image.setImagePi(imageMap.get("public_id"));
        		board.addImage(image);  
        	}
        }
        // 모임에 게시물 추가 로직
        return moimService.addBoardToMoim(board);
    }
    
    @GetMapping("/board/{boardId}")
    public Board getBoardDetail(@PathVariable String boardId) {
        return moimService.getBoardById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시물을 찾을 수 없습니다."));
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
    	Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    	Pageable pageable = new PageRequest(page, size, sort);
        return moimService.getBoardsByMoimIdPaged(moimId, pageable);
    }
    
}
