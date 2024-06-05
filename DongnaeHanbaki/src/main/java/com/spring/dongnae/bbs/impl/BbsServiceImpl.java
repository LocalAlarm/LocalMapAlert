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
	
	@Override //페이징 처리 위한 게시글목록/ 비긴,엔드,맵인덱스 필요
	public List<BbsVO> getPagingBbsList(String begin, String end, BbsVO vo) {
		Map<String, String> map = new HashMap<>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("mapIdx", vo.getMapIdx());
		return bbsDAO.getPagingBbsList(map);
	}
	public List<BbsVO> getPagingBbsList(String begin, String end, String mapIdx) {
		BbsVO vo = new BbsVO();
		vo.setMapIdx(mapIdx);
		return getPagingBbsList(begin,end,vo);
	}

	@Override //지도 마킹 위한 게시글 목록, 24시간내 작성글 가져오기/ 맵인덱스필요
	public List<BbsVO> getMarkingBbsList(BbsVO vo) {
		return bbsDAO.getMarkingBbsList(vo);
	}

	//이하 bbsIdx 필요
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

	//삭제된 게시글들 리턴 (필요한가?)
	@Override
	public List<BbsVO> getDelBbsList() {
		return bbsDAO.getDelBbsList();
	}

}
