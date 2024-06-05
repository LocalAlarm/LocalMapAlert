package com.spring.dongnae.bbs.dao;

import java.util.List;

import com.spring.dongnae.bbs.MapVO;

public interface MapDAO {
	List<MapVO> getMapList(MapVO vo);
	MapVO getMap(MapVO vo);
	int insertMap(MapVO vo);
	int updateMap(MapVO vo);
	int deleteMap(MapVO vo);
}
