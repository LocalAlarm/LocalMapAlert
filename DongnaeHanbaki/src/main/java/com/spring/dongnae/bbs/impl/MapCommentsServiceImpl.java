package com.spring.dongnae.bbs.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.dongnae.bbs.MapCommentsService;
import com.spring.dongnae.bbs.MapCommentsVO;
import com.spring.dongnae.bbs.dao.MapCommentsDAO;

@Service("mapCommentsService")
public class MapCommentsServiceImpl implements MapCommentsService {
	MapCommentsDAO mapCommentsDAO;
	@Override
	public List<MapCommentsVO> getCommentList(MapCommentsVO vo) {
		return mapCommentsDAO.getCommentList(vo);
	}

	@Override
	public MapCommentsVO getComment(MapCommentsVO vo) {
		return mapCommentsDAO.getComment(vo);
	}

	@Override
	public int insertComment(MapCommentsVO vo) {
		return mapCommentsDAO.insertComment(vo);
	}

	@Override
	public int updateComment(MapCommentsVO vo) {
		return mapCommentsDAO.updateComment(vo);
	}

	@Override
	public int deleteComment(MapCommentsVO vo) {
		return mapCommentsDAO.deleteComment(vo);
	}

}
