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
import com.spring.dongnae.socket.error.DuplicateTitleException;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Image;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.service.BoardService;
import com.spring.dongnae.socket.service.MoimService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController
@RequestMapping("/moim")
public class MoimController {
	@Autowired
	private MoimService moimService;
	@Autowired
	private BoardService boardService;
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
        } catch (DuplicateTitleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 모임입니다. " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모임 생성에 실패했습니다: " + e.getMessage());
        }
    }
    
    @GetMapping(value = "/{moimId}", produces = "application/json; charset=UTF-8")
    public MoimDto getMoimDtoInfo(@PathVariable String moimId) throws Exception {
        return moimService.getMoimDtoInfo(moimId);
    }
	
	@PostMapping("/{moimName}/add-participants")
	public Moim addParticipantToMoim(@PathVariable String moimName) {
		return moimService.addParticipantToMoim(moimName, getAuthenticInfo.GetToken());
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
        		System.out.println(imageFile.getSize());
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
    public ResponseEntity<String> deleteBoard(@PathVariable String boardId) {
    	if (moimService.deleteBoard(boardId)) {
    		return ResponseEntity.ok("삭제에 성공했습니다.");
    	} else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("접근에 실패했습니다.");
    	}
    }
    
    @PostMapping("/{boardId}/likes")
    public boolean toggleLikeToPost(@PathVariable String boardId, @RequestParam String userEmail) {
        return moimService.toggleLikeToBoard(boardId, userEmail);
    }
    
    @PostMapping("/{boardId}/add-comments")
    public void addCommentToBoard(@PathVariable String boardId, @RequestBody Comment comment) {
    	System.out.println(comment.toString());
    	moimService.addCommentToBoard(boardId, comment);
    }
    
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public boolean deleteCommentFromBoard(@PathVariable String boardId, @PathVariable String commentId) {
    	return moimService.deleteCommentFromBoard(boardId, commentId);
    }
    
    @GetMapping("/get-comments/{boardId}")
    public List<Comment> getCommetList(@PathVariable String boardId) {
    	System.out.println("asdsa");
    	return boardService.getCommentList(boardId);
    }
    
    @GetMapping("/{moimId}/all-boards")
    public List<Board> getBoardsByMoimId(@PathVariable String moimId) {
    	return moimService.getBoardByMoimId(moimId);
    }
    
    @GetMapping("/{moimId}/boards/count")
    public long getBoardCount(@PathVariable String moimId) {
        return boardService.countBoardsByMoimId(moimId);
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
