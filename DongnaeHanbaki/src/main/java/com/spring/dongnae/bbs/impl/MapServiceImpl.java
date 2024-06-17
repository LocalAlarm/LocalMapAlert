//package com.spring.dongnae.bbs.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.spring.dongnae.bbs.MapService;
//import com.spring.dongnae.bbs.MapVO;
//import com.spring.dongnae.bbs.dao.MapDAO;
//
//@Service("mapService")
//public class MapServiceImpl implements MapService{
//	@Autowired
//	private MapDAO mapDAO;
//	
//	@Override//유저이메일(제작자) 또는 등급으로 리스트얻기, 값이 있으면 조건 검색
//	public List<MapVO> getMapList(MapVO vo) {
//		return mapDAO.getMapList(vo);
//	}
////	public List<MapVO> getMapListByEmail(String userEmail) {
////		MapVO vo = new MapVO();
////		vo.setUserEmail(userEmail);
////		return getMapList(vo);
////	}
//	
//	//이하 mapIdx 필요
//	@Override
//	public MapVO getMap(MapVO vo) {
//		return mapDAO.getMap(vo);
//	}
//
//	@Override
//	public int insertMap(MapVO vo) {
//		return mapDAO.insertMap(vo);
//	}
//
//	@Override
//	public int updateMap(MapVO vo) {
//		return mapDAO.updateMap(vo);
//	}
//
//	@Override
//	public int deleteMap(MapVO vo) {
//		return mapDAO.deleteMap(vo);
//	}
//
//}
