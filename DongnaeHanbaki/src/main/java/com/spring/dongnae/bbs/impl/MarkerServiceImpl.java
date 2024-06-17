package com.spring.dongnae.bbs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.bbs.MarkerService;
import com.spring.dongnae.bbs.MarkerVO;
import com.spring.dongnae.bbs.dao.MarkerDAO;

@Service("markerService")
public class MarkerServiceImpl implements MarkerService{
	@Autowired
	private MarkerDAO markerDAO;
	
	@Override//userEmail 또는 limitRole 필요, 있는 값으로 검색
	public List<MarkerVO> getMakerList(MarkerVO vo) {
		return markerDAO.getMakerList(vo);
	}

	@Override
	public MarkerVO getMaker(MarkerVO vo) {
		return markerDAO.getMaker(vo);
	}

	@Override
	public int insertMaker(MarkerVO vo) {
		return markerDAO.insertMaker(vo);
	}

	@Override
	public int updateMaker(MarkerVO vo) {
		return markerDAO.updateMaker(vo);
	}

	@Override
	public int deleteMaker(MarkerVO vo) {
		return markerDAO.deleteMaker(vo);
	}

}
