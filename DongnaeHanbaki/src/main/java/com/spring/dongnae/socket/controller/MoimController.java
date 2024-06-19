package com.spring.dongnae.socket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.socket.service.MoimService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController("/moim")
public class MoimController {
	@Autowired
	private ImageUploadController imageUploadController;
	@Autowired
	private GetAuthenticInfo getAuthenticInfo;
	@Autowired
	private UserRoomsRepository userRoomsRepository;
	@Autowired
	private MoimService moimService;
	
	@PostMapping("/createMoim")
	public ResponseEntity<String> createMoim(            
			@RequestParam("title") String title,
            @RequestParam("introduce") String introduce,
            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic) {
        try {
            Map<String, String> imageMap = null;
            if (profilePic != null && !profilePic.isEmpty()) {
                imageMap = imageUploadController.uploadImage(profilePic);
            }
            if (setMoimValue(title, introduce, imageMap)) {            	
            	return ResponseEntity.ok("모임이 성공적으로 생성되었습니다.");
            } else {
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모임 생성에 실패했습니다. 모임의 데이터가 저장되지 않았습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("모임 생성에 실패했습니다: " + e.getMessage());
        }
	}
	
	@PostMapping("/{moimId}/add-participants/{token}")
	public Moim addParticipantToMoim(@PathVariable String moimId, @PathVariable String token) {
		return moimService.addParticipantToMoim(moimId, token);
	}
	
    @PostMapping("/{groupId}/board")
    public Board addPostToGroup(@PathVariable String groupId, @RequestBody Board board) {
        return moimService.addBoardToMoim(groupId, board);
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
    
    @GetMapping("/{moimId}/boards")
    public List<Board> getBoardsByMoimId(@PathVariable String moimId) {
    	return moimService.getBoardByMoimId(moimId);
    }
    
    @GetMapping("/userRooms/{token}")
    public List<Moim> getMoimsByUser(@PathVariable String token) {
    	return moimService.getMoimByToken(token);
    }
	
	private boolean setMoimValue(String title, String introduce, Map<String, String>imageMap) {
		Moim moim = new Moim();
		moim.setName(title);
		moim.setDescription(introduce);
		if (imageMap != null) {
			moim.setProfilePic(imageMap.get("url"));
			moim.setProfilePicPI(imageMap.get("public_id"));
		} else {
			moim.setProfilePic("https://res.cloudinary.com/djlee4yl2/image/upload/v1713834954/logo/github_logo_icon_tpisfg.png");
		}
		try {
			UserRooms userRoom = userRoomsRepository.findById(getAuthenticInfo.GetToken()).get();
			moim.setLeader(userRoom);
			moimService.createMoim(moim);
			userRoomsRepository.save(userRoom);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
