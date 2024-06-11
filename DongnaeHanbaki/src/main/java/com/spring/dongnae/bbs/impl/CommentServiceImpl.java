package com.spring.dongnae.bbs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.bbs.CommentService;
import com.spring.dongnae.bbs.CommentVO;
import com.spring.dongnae.bbs.dao.CommentDAO;

@Service("commentService")
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDAO commentDAO;
	
	//게시글 인덱스 필요
	@Override
	public List<CommentVO> getCommentList(CommentVO vo) {
		return commentDAO.getCommentList(vo);
	}
	public List<CommentVO> getCommentList(String bbsIdx) {
		CommentVO vo = new CommentVO();
		vo.setBbsIdx(bbsIdx);
		return getCommentList(vo);
	}

	//이하 댓글인덱스 필요
	@Override
	public CommentVO getComment(CommentVO vo) {
		return commentDAO.getComment(vo);
	}

	@Override
	public int insertComment(CommentVO vo) {
		return commentDAO.insertComment(vo);
	}

	@Override
	public int updateComment(CommentVO vo) {
		return commentDAO.updateComment(vo);
	}

	@Override
	public int deleteComment(CommentVO vo) {
		return commentDAO.deleteComment(vo);
	}

}
