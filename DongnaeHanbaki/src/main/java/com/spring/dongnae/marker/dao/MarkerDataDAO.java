package com.spring.dongnae.marker.dao;

import java.util.List;
import java.util.Map;

import com.spring.dongnae.marker.vo.MarkerDataVO;

public interface MarkerDataDAO {

	List<MarkerDataVO> getMenu(int marker_idx);
	List<MarkerDataVO> allMenu(MarkerDataVO vo);
    List<MarkerDataVO> getRealTimeAccidents(int marker_idx);
    List<MarkerDataVO> getAllAccidents(int marker_idx);

	void insertMarker(MarkerDataVO vo);

}
