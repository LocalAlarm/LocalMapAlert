package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.repo.BoardRepository;
import com.spring.dongnae.socket.repo.MoimRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Moim;
import com.spring.dongnae.socket.scheme.UserRooms;

@Service
public class MoimService {
	
    @Autowired
    private MoimRepository moimRepository;
    @Autowired
    private UserRoomsRepository userRoomsRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    
    public Moim createMoim(Moim moim) {
    	ChatRoom chatRoom = chatRoomService.createChatRoom();
    	moim.setChatRoom(chatRoom);
    	return moimRepository.save(moim);
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
    
    public Board addBoardToMoim(String moimId, Board board) {
    	Optional<Moim> moimOptional = moimRepository.findById(moimId);
    	if (moimOptional.isPresent()) {
    		Moim moim = moimOptional.get();
    		board.setMoim(moim);
    		Board savedBoard = boardRepository.save(board);
    		moim.addBoard(savedBoard);
    		moimRepository.save(moim);
    		return savedBoard;
    	}
    	return null;
    }
    
    public boolean deleteBoard(String boardId) {
    	Optional<Board> boardOptional = boardRepository.findById(boardId);
    	if (boardOptional.isPresent()) {
    		Board board = boardOptional.get();
    		Moim moim = board.getMoim();
    		if (moim != null) {
    			moim.getBoards().remove(board);
    			moimRepository.save(moim);
    		}
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
    	if (boardOptional.isPresent()) {
    		Board board = boardOptional.get();
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
    
    public List<Board> getBoardByMoimId(String moimId) {
    	return boardRepository.findByMoimId(moimId);
    }
    
    public List<Moim> getMoimByToken(String token) {
    	Optional<UserRooms> userRoomsOptional = userRoomsRepository.findById(token);
    	return userRoomsOptional.map(UserRooms::getMoims).orElse(null);
    }
}
