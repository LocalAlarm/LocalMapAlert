package com.spring.dongnae.marker.dao;

import java.util.List;
import java.util.Map;

import com.spring.dongnae.marker.vo.MarkerDataVO;

public interface MarkerDataDAO {

	List<MarkerDataVO> getMenu(int marker_idx);
	List<MarkerDataVO> allMenu(MarkerDataVO vo);
    List<MarkerDataVO> getRealTimeAccidents(int marker_idx);
    List<MarkerDataVO> getAllAccidents(int marker_idx);
    List<MarkerDataVO> getNearAccidents(int marker_idx);
    List<MarkerDataVO> allEvents(MarkerDataVO vo);
    List<MarkerDataVO> nearEvents(MarkerDataVO vo);
    List<MarkerDataVO> realEvents(MarkerDataVO vo);

	void insertMarker(MarkerDataVO vo);

}