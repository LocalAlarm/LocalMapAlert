package com.spring.dongnae.marker.service;

import java.util.List;

import com.spring.dongnae.marker.vo.MarkerDataVO;

public interface MarkerDataService {
	List<MarkerDataVO> allMenu(MarkerDataVO vo);
		List<MarkerDataVO> getMenu(int marker_idx);
		void saveMarker(MarkerDataVO vo);
		List<MarkerDataVO> getRealTimeEvents(int marker_idx);
		List<MarkerDataVO> getAllAccidents(int marker_idx);
	

}