package com.spring.dongnae.custom.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.custom.scheme.CustomMarker;
import com.spring.dongnae.custom.service.CustomService;
import com.spring.dongnae.map.service.MapService;
import com.spring.dongnae.map.vo.MapVO;

@Controller
public class CustomController {
	
	private final MapService mapService;
	private final CustomService customService;
//	private final ObjectMapper objectMapper;

	@Autowired
	public CustomController(MapService mapService, CustomService customService) {
		this.mapService = mapService;
		this.customService = customService;
		System.out.println("========= customController() 객체생성");
	}
	
	@PostMapping("/saveMap")
	@ResponseBody
	public boolean saveMap(HttpServletRequest request, HttpSession session) throws IOException {
		boolean check = false;
		String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//		session.getAttribute("user");
		System.out.println("커스텀 맵 데이터 받기 성공!!" + jsonString);
		String email = "test5@naver.com";
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			
            // JSON 문자열을 JsonNode 객체로 변환
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // center 필드 추출
            String center = jsonNode.get("center").asText();
            
            // 괄호 제거 후 분리
            String coordinates = center.substring(1, center.length() - 1);
            String[] parts = coordinates.split(", ");
            
         // 좌표 추출
            double la = Double.parseDouble(parts[0]);
            double ma = Double.parseDouble(parts[1]);

            // 변수에 저장
            double centerLongitude = la;
            double centerLatitude = ma;
            // level 필드 추출
            String level = jsonNode.get("level").asText();
            String title = jsonNode.get("title").asText();
            String content = jsonNode.get("content").asText();

            // 추출한 필드 값 출력
//            System.out.println("centerLongitude: " + centerLongitude);
//            System.out.println("centerLatitude: " + centerLatitude);
//            System.out.println("Level: " + level);
            MapVO vo = new MapVO();
            vo.setUserEmail(email);
            vo.setCenterIatitude(centerLatitude);
            vo.setCenterIogitude(centerLongitude);
            vo.setViewLevel(level);
            vo.setTitle(title);
            vo.setContent(content);
//            System.out.println("삽입할 map : " + vo);
            
            if( mapService.insertMap(vo) > 0) {
            	check = true;
            	ObjectMapper mapper = new ObjectMapper();
            	CustomMarker customMarker = mapper.readValue(jsonString, CustomMarker.class);
            	customMarker.setMapIdx(12);
            	System.out.println("커스텀 스키마로 변경! : " + customMarker);
            	customService.saveMarker(customMarker);
            	
            }
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
		return check;
	}
}
