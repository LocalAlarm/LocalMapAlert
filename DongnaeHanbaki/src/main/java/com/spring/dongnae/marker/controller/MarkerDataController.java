package com.spring.dongnae.marker.controller;

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
    
    @RequestMapping("/Events")
    @ResponseBody
    public List<MarkerDataVO> getEvents() {
    	int marker_idx = 1;
    	return markerdataService.getMenu(marker_idx);
    }
    
    @RequestMapping("/EventAccidents")
    @ResponseBody
    public List<MarkerDataVO> getEventAccidents() {
        int marker_idx = 2;
        return markerdataService.getMenu(marker_idx);
    }
    
    @RequestMapping("/all")
    @ResponseBody
    public List<MarkerDataVO> allMenu(MarkerDataVO vo) {
    	return markerdataService.allMenu(vo);
    }
    
    @PostMapping("/saveM")
    public ResponseEntity<String> saveMarker(@RequestBody MarkerDataVO vo) {
        System.out.println("세이브 마커 컨트롤러");
        markerdataService.saveMarker(vo);
        return ResponseEntity.ok("마커 저장.");
    }

 
}
