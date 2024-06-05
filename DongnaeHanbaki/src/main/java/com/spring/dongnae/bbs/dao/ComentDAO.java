package com.spring.dongnae.bbs.dao;

import java.util.List;

import com.spring.dongnae.bbs.CommentVO;

public interface ComentDAO {
	List<CommentVO> getCommentList(CommentVO vo); //게시글번호로 댓글목록 얻기
	CommentVO getComment(CommentVO vo);
	int insertComment(CommentVO vo);
	int updateComment(CommentVO vo);
	int deleteComment(CommentVO vo);
}
