package com.spring.dongnae.bbs;

import java.util.List;

import com.spring.dongnae.bbs.CommentVO;

public interface CommentService {
	// WHERE DEL_YN = 0 적용중
	
	//게시글 인덱스 필요
	List<CommentVO> getCommentList(CommentVO vo); //게시글번호로 댓글목록 얻기
	
	//댓글인덱스 필요
	CommentVO getComment(CommentVO vo);
	int insertComment(CommentVO vo);
	int updateComment(CommentVO vo);
	int deleteComment(CommentVO vo);
}
