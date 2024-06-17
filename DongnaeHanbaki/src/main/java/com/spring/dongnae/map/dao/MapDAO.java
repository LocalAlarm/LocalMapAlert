package com.spring.dongnae.map.dao;

import java.util.List;

import com.spring.dongnae.map.vo.MapVO;

public interface MapDAO {
	//WHERE DEL_YN = 0 적용중
	
	//유저이메일(제작자) 또는 등급으로 리스트얻기, 값이 있으면 조건 검색
	List<MapVO> getMapList(MapVO vo);
	
	//mapIdx 필요
	MapVO getMap(MapVO vo);
	int insertMap(MapVO vo);
	int updateMap(MapVO vo);
	int deleteMap(MapVO vo);
	
}
