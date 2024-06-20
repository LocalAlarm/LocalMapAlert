package com.spring.dongnae.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.map.dao.MapDAO;
import com.spring.dongnae.map.vo.MapVO;

@Service("mapService")
public class MapServiceImpl implements MapService {
	
	@Autowired
	private MapDAO mapDAO;
	
	public MapServiceImpl() {
		System.out.println(">> MapServiceImpl() 객체생성");
	}

	@Override//유저이메일(제작자) 또는 등급으로 리스트얻기, 값이 있으면 조건 검색
	public List<MapVO> getMapList(MapVO vo) {
		return mapDAO.getMapList(vo);
	}
	
	@Override //title or content or title+content 필요
	public List<MapVO> getSearchMapList(MapVO vo) {
		return mapDAO.getSearchMapList(vo);
	}

	@Override //mapIdx 필요
	public MapVO getMap(MapVO vo) {
		return mapDAO.getMap(vo);
	}

	@Override
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
