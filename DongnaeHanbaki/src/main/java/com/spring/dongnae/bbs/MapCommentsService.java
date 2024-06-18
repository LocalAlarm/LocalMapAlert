package com.spring.dongnae.bbs;

import java.util.List;

import com.spring.dongnae.bbs.CommentVO;
import com.spring.dongnae.bbs.MapCommentsVO;

public interface MapCommentsService {
	// WHERE DEL_YN = 0 적용중
	
	//맵 인덱스 필요
	List<MapCommentsVO> getCommentList(MapCommentsVO vo); //게시글번호로 댓글목록 얻기
	
	//댓글인덱스 필요
	MapCommentsVO getComment(MapCommentsVO vo);
	int insertComment(MapCommentsVO vo);
	int updateComment(MapCommentsVO vo);
	int deleteComment(MapCommentsVO vo);
}
