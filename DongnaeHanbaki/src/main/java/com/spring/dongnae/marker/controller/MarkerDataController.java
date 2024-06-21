package com.spring.dongnae.marker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dongnae.marker.service.MarkerDataService;
import com.spring.dongnae.marker.vo.MarkerDataVO;

@Controller
public class MarkerDataController {

	@Autowired
	private MarkerDataService markerdataService;

	@RequestMapping("/all")
	@ResponseBody
	public List<MarkerDataVO> allMenu(MarkerDataVO vo) {
		return markerdataService.allMenu(vo);
	}
	@RequestMapping("/AllAccidents")
	@ResponseBody
	public List<MarkerDataVO> getAllAccidents() {
		int marker_idx = 1;
		return markerdataService.getMenu(marker_idx);
	}

	@RequestMapping("/RealTimeAccidents")
	@ResponseBody
	public List<MarkerDataVO> RealTimeEvents() {
		int marker_idx = 1;
		return markerdataService.getRealTimeEvents(marker_idx);
	}

	
	@RequestMapping("/NearAccidents")
	@ResponseBody 
	public List<MarkerDataVO> AllAccidents() { 
		int marker_idx = 1;
		return markerdataService.getNearAccidents(marker_idx); 
	}
	
	@RequestMapping("/Events")
	@ResponseBody
	public List<MarkerDataVO> getEvents(MarkerDataVO vo) {
		System.out.println("1");
	    return markerdataService.getEvents(vo);
	}
	
	@PostMapping("/saveM")
	public ResponseEntity<String> saveMarker(@RequestBody MarkerDataVO vo) {
		markerdataService.saveMarker(vo);
		return ResponseEntity.ok("마커 저장.");
	}

}