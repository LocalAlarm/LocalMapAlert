package com.spring.dongnae.bbs.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dongnae.bbs.BbsService;
import com.spring.dongnae.bbs.BbsVO;

@Controller
public class BbsController {
    
    @Autowired
    private BbsService bbsService; 
    
    @RequestMapping("/getEventAccidents")
    @ResponseBody
    public List<BbsVO> getEventAccidents() {
        String content = "사건사고";
        return bbsService.getMenu(content);
    }
    
    @RequestMapping("/getEvents")
    @ResponseBody
    public List<BbsVO> getEvents() {
        String content = "이벤트";
        return bbsService.getMenu(content);
    }
    
    @RequestMapping("/allMenu")
    @ResponseBody
    public List<BbsVO> allMenu(BbsVO vo) {
    	return bbsService.allMenu(vo);
    }
    
    @PostMapping("/saveMarker")
    public ResponseEntity<String> saveMarker(@RequestBody BbsVO vo) {
        System.out.println("세이브 마커 컨트롤러");
        bbsService.saveMarker(vo);
        return ResponseEntity.ok("마커 저장.");
    }

 
}
