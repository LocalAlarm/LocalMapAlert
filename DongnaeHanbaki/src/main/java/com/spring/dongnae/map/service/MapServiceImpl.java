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

	@Override //openYn 또는 userEmail 필요
	public List<MapVO> getMapList(MapVO vo) {
		return mapDAO.getMapList(vo);
	}
	
	@Override //mapIdx 필요
	public MapVO getMap(MapVO vo) {
		return mapDAO.getMap(vo);
	}

	@Override //MapVO 필요
	public int insertMap(MapVO vo) {
		return mapDAO.insertMap(vo);
	}

	@Override //mapIdx 필요
	public int updateMap(MapVO vo) {
		return mapDAO.updateMap(vo);
	}

	@Override //mapIdx 필요
	public int deleteMap(MapVO vo) {
		return mapDAO.deleteMap(vo);
	}

}
