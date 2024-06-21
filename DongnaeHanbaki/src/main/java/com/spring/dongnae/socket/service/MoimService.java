package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.socket.dto.MoimDto;
import com.spring.dongnae.socket.repo.BoardRepository;
import com.spring.dongnae.socket.repo.MoimRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Service
public class MoimService {
	@Autowired
	private ImageUploadController imageUploadController;
    @Autowired
    private MoimRepository moimRepository;
    @Autowired
    private UserRoomsRepository userRoomsRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
	private GetAuthenticInfo getAuthenticInfo;
    
    public Moim createMoim(String title, String introduce, MultipartFile profilePic) throws Exception {
    	Map<String, String> imageMap = null;
    	if (profilePic != null && !profilePic.isEmpty()) {
            imageMap = imageUploadController.uploadImage(profilePic);
        }
    	Moim moim = new Moim(title, introduce, imageMap);
        UserRooms userRoom = userRoomsRepository.findById(getAuthenticInfo.GetToken()).orElseThrow(() -> new Exception("User not found"));
        moim.setLeader(userRoom);
        moim.setChatRoomId(chatRoomService.createChatRoom());
        moimRepository.save(moim);
        userRoomsRepository.save(userRoom);
        
        return moim;
    }
    
    public Moim addParticipantToMoim(String moimId, String token) {
    	Optional<Moim> moimOptional = moimRepository.findById(moimId);
    	Optional<UserRooms> userRoomsOptional = userRoomsRepository.findById(token);
    	if (moimOptional.isPresent() && userRoomsOptional.isPresent()) {
    		Moim moim = moimOptional.get();
    		UserRooms userRooms = userRoomsOptional.get();
    		moim.addParticipant(userRooms);
    		userRooms.addMoim(moim);
    		userRoomsRepository.save(userRooms);
    		return moimRepository.save(moim);
    	}
    	return null;
    }
    
    public Board addBoardToMoim(Board board) {
    	Optional<Moim> moimOptional = moimRepository.findById(board.getMoimId());
    	if (moimOptional.isPresent()) {
    		Moim moim = moimOptional.get();
    		board.setMoim(moim);
    		Board savedBoard = boardRepository.save(board);
    		return savedBoard;
    	}
    	return null;
    }
    
    public boolean deleteBoard(String boardId) {
    	Optional<Board> boardOptional = boardRepository.findById(boardId);
    	if (boardOptional.isPresent()) {
    		Board board = boardOptional.get();
    		boardRepository.delete(board);
    		return true;
    	}
    	return false;
    }
    
    public boolean toggleLikeToBoard(String boardId, String email) {
    	Optional<Board> boardOptional = boardRepository.findById(boardId);
    	if (boardOptional.isPresent()) {
    		Board board = boardOptional.get();
    		board.toggleLike(email);
    		boardRepository.save(board);
    		return true;
    	}
    	return false;
    }
    
    public void addCommentToBoard(String boardId, Comment comment) {
    	Optional<Board> boardOptional = boardRepository.findById(boardId);
    	if (boardOptional.isPresent() && getAuthenticInfo.GetToken() != null) {
    		Board board = boardOptional.get();
    		comment.setAuthor(getAuthenticInfo.GetToken());
    		board.addComment(comment);
    		boardRepository.save(board);
    	}
    }
    
    public boolean deleteCommentFromBoard(String boardId, String commentId) {
    	Optional<Board> boardOptional = boardRepository.findById(boardId);
    	if (boardOptional.isPresent()) {
    		Board board = boardOptional.get();
    		boolean result = board.deleteComment(commentId);
    		if (result) {
    			boardRepository.save(board);
    		}
    		return result;
    	}
    	return false;
    }
    
    // 모임 정보 가져오기
    public MoimDto getMoimDtoInfo(String moimId) throws Exception {
    	Moim moim = moimRepository.findById(moimId).orElseThrow(() -> new Exception("Moim not found"));
    	MoimDto moimDTO = new MoimDto();
    	moimDTO.setId(moim.getId());
    	moimDTO.setChatId(moim.getChatRoomId());
    	moimDTO.setName(moim.getName());
    	moimDTO.setProfilePic(moim.getProfilePic());
    	
        return moimDTO;
    }
    
    // 게시글 페이징 처리하여 가져오기
    public Page<Board> getBoardsByMoimIdPaged(String moimId, Pageable pageable) {
        return boardRepository.findByMoimId(moimId, pageable);
    }
    
    public List<Board> getBoardByMoimId(String moimId) {
    	return boardRepository.findByMoimId(moimId);
    }
    
    public Optional<Board> getBoardById(String boardId) {
        return boardRepository.findById(boardId);
    }
    
//    public List<Moim> getMoimByToken(String token) {
//    	Optional<UserRooms> userRoomsOptional = userRoomsRepository.findById(token);
//    	return userRoomsOptional.map(UserRooms::getMoims).orElse(null);
//    }
}
