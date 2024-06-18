package com.spring.dongnae.custom.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.custom.scheme.CustomMarker;
import com.spring.dongnae.custom.service.CustomService;
import com.spring.dongnae.map.service.MapService;
import com.spring.dongnae.map.vo.MapVO;
import com.spring.dongnae.user.vo.UserVO;

@Controller
public class CustomController {
   
   private final MapService mapService;
   private final CustomService customService;
//   private final ObjectMapper objectMapper;

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
//      session.getAttribute("user");
      System.out.println("커스텀 맵 데이터 받기 성공!!" + jsonString);
      String email = "test18@naver.com";
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
               System.out.println("map 입력 성공!!!");
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
   
   @RequestMapping("/customMap")
	public String customMap(MapVO mapVO, Model model,HttpSession session) {
		//커스텀 맵 불러오는 창
		//공개된것 검색
		mapVO.setOpenYn("1");
		System.out.println("mapVO : " + mapVO);//-------------------test code-----------------
		//다른사람이 만든 커스텀 맵 중 공개된것 목록 불러옴
		List<MapVO> openCustomMapList = mapService.getMapList(mapVO);
		System.out.println("openCustomMapList : " + openCustomMapList.toString());//-------------------test code-----------------
		model.addAttribute("openCustomMapList", openCustomMapList);
		
		//로그인 여부 확인-> true : 내 커스텀 맵 불러옴
		UserVO loginUser = (UserVO)session.getAttribute("user");
		System.out.println("loginUser : " + loginUser);//-------------------test code-----------------
		if(loginUser != null && loginUser.getEmail() != null) {
			mapVO.setOpenYn(null);
			mapVO.setUserEmail(loginUser.getEmail());
			List<MapVO> myCustomMapList = mapService.getMapList(mapVO);
			System.out.println("myCustomMapList : " + myCustomMapList.toString());//-------------------test code-----------------
			model.addAttribute("myCustomMapList", myCustomMapList);
		}
		System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
		return "map/customMap"; 
	}
	
	@RequestMapping("/createMap")
	public String ceateMap() {
		//커스텀맵 제작 페이지로 이동
		//로그인 여부 확인 필요, false : 로그인 페이지로 이동
		return "map/createMap"; 
	}
	
	@RequestMapping("/oneCustMap")
	public String oneCustMap() {
		//커스텀맵 하나 상세보기창 이동
		//mapIdx로 불러온 customMapVO 필요
		//커스텀맵에서 사용한 마커종류 리스트 필요
		//표시한 마커목록 리스트 필요
		//댓글리스트 필요
		return "map/oneCustMap"; 
	}
	
	@RequestMapping("/updateCustMap")
	public String updateCustMap() {
		//커스텀맵 편집페이지 이동
		//로그인여부 확인 필요 , false : 로그인 페이지로 이동
		//mapIdx로 불러온 customMapVO 필요
		//커스텀맵에서 사용한 마커종류 리스트 필요
		//표시한 마커목록 리스트 필요
		return "map/updateCustMap"; 
	}
}
