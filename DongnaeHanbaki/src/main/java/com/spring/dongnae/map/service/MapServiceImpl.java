package com.spring.dongnae.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.map.dao.MapDAO;
import com.spring.dongnae.map.vo.MapVO;

@Service
public class MapServiceImpl implements MapService {
	
	@Autowired
	private MapDAO mapDAO;
	
	public MapServiceImpl() {
		System.out.println(">> MapServiceImpl() 객체생성");
	}

	@Override
	public List<MapVO> getMapList(MapVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapVO getMap(MapVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertMap(MapVO vo) {
		return mapDAO.insertMap(vo);
	}

	@Override
	public int updateMap(MapVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMap(MapVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}
}
