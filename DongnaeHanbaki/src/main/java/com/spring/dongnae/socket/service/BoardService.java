package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.socket.error.AccessDenied;
import com.spring.dongnae.socket.repo.BoardRepository;
import com.spring.dongnae.socket.scheme.Board;
import com.spring.dongnae.socket.scheme.Comment;
import com.spring.dongnae.socket.scheme.Image;
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
	
    public List<Comment> getCommentList(String boardId) {
        return boardRepository.findById(boardId)
            .map(Board::getComments)
            .orElseThrow(() -> new RuntimeException("Board not found"));
    }
    
    public Board updateBoard(String moimId, String boardId, String title, String content, MultipartFile[] images, List<String> imageOptions) throws ResourceNotFoundException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시물을 찾을 수 없습니다."));
        
        if (!board.getAuthor().equals(getAuthenticInfo.GetToken())) {
            throw new AccessDenied("사용자가 이 게시물을 수정할 권한이 없습니다.");
        }
        
        // 보드 정보를 업데이트
        board.setTitle(title);
        board.setContent(content);

        // 체크박스 상태 처리
        if (imageOptions != null) {
            for (String image : imageOptions) {
            	System.out.println("이미" + image);
                board.deleteImage(image);
            }
        }

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

        // 보드 업데이트 로직
        return boardRepository.save(board);
    }
}
