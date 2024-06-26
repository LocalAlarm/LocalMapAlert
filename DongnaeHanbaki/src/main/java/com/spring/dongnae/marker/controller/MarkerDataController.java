package com.spring.dongnae.marker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/allevents")
	@ResponseBody
	public List<MarkerDataVO> allEvents(MarkerDataVO vo) {
		return markerdataService.allEvents(vo);
	}
	
	@RequestMapping("/nearEvents")
	@ResponseBody
	public List<MarkerDataVO> nearEvents(MarkerDataVO vo) {
		return markerdataService.nearEvents(vo);
	}
	
	@PostMapping("/saveM")
	public ResponseEntity<String> saveMarker(@RequestBody MarkerDataVO vo) {
		markerdataService.saveMarker(vo);
		return ResponseEntity.ok("마커 저장.");
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<MarkerDataVO> searchMarkers(@RequestParam String keyword) {
	    return markerdataService.searchMarkers(keyword);
	}

	@RequestMapping("/RealTimeEvents")
	@ResponseBody
	public List<MarkerDataVO> realEvents(MarkerDataVO vo) {
		return markerdataService.realEvents(vo);
	}

	
}