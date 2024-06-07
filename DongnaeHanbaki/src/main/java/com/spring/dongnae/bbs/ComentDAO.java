package com.spring.dongnae.bbs;

import java.util.List;

public interface ComentDAO {
	List<CommentVO> getCommentList(String bbsIdx); //게시글번호로 댓글목록 얻기
	CommentVO getComment(String commentIdx);
	int insertComment(CommentVO vo);
	int updateComment(CommentVO vo);
	int deleteComment(String commentIdx);
}
