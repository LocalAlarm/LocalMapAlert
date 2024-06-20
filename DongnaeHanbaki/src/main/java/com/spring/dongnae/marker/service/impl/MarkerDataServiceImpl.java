package com.spring.dongnae.marker.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.marker.dao.MarkerDataDAO;
import com.spring.dongnae.marker.service.MarkerDataService;
import com.spring.dongnae.marker.vo.MarkerDataVO;

@Service("MarkerDataService")
public class MarkerDataServiceImpl implements MarkerDataService{
	@Autowired
	private MarkerDataDAO markerdataDAO;
	
	@Override
	public List<MarkerDataVO> getMenu(int marker_idx) {
		return markerdataDAO.getMenu(marker_idx);
	}
	
	@Override
	public List<MarkerDataVO> getRealTimeEvents(int marker_idx) {
		return markerdataDAO.getRealTimeAccidents(marker_idx);
	}
	
	@Override
	public List<MarkerDataVO> getAllAccidents(int marker_idx) {
		return markerdataDAO.getAllAccidents(marker_idx);
	}
	
	@Override
	public List<MarkerDataVO> allMenu(MarkerDataVO vo) {
		return markerdataDAO.allMenu(vo);
	}
	
	public void saveMarker(MarkerDataVO vo) {
	    markerdataDAO.insertMarker(vo);
	}

	@Override
	public List<MarkerDataVO> getNearAccidents(int marker_idx) {
		return markerdataDAO.getNearAccidents(marker_idx);
	}
}