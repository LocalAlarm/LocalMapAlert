package com.spring.dongnae.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.bbs.BbsService;
import com.spring.dongnae.bbs.BbsVO;
import com.spring.dongnae.bbs.dao.BbsDAO;

@Service("bbsService")
public class BbsServiceImpl implements BbsService{
	@Autowired
	private BbsDAO bbsDAO;
	
	@Override
	public List<BbsVO> getPagingBbsList(String begin, String end, BbsVO vo) {
		Map<String, String> map = new HashMap<>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("mapIdx", vo.getMapIdx());
		return bbsDAO.getPagingBbsList(map);
	}

	@Override
	public List<BbsVO> getMarkingBbsList(BbsVO vo) {
		return bbsDAO.getMarkingBbsList(vo);
	}

	@Override
	public BbsVO getBbs(BbsVO vo) {
		return bbsDAO.getBbs(vo);
	}

	@Override
	public int insertBbs(BbsVO vo) {
		return bbsDAO.insertBbs(vo);
	}

	@Override
	public int updateBbs(BbsVO vo) {
		return bbsDAO.updateBbs(vo);
	}

	@Override
	public int deleteBbs(BbsVO vo) {
		return bbsDAO.deleteBbs(vo);
	}

	@Override
	public List<BbsVO> getDelBbsList() {
		return bbsDAO.getDelBbsList();
	}

}
