package com.spring.dongnae.marker.service;

import java.util.List;

import com.spring.dongnae.marker.vo.MarkerDataVO;

public interface MarkerDataService {
		List<MarkerDataVO> getMenu(int marker_idx);
		List<MarkerDataVO> allMenu(MarkerDataVO vo);
		void saveMarker(MarkerDataVO vo);
	

}