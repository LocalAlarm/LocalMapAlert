package com.spring.dongnae.custom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.bbs.MapCommentsService;
import com.spring.dongnae.bbs.MapCommentsVO;
import com.spring.dongnae.custom.scheme.CustomMarker;
import com.spring.dongnae.custom.service.CustomService;
import com.spring.dongnae.map.service.MapService;
import com.spring.dongnae.map.vo.MapVO;
import com.spring.dongnae.user.vo.UserVO;

@Controller
public class CustomController {
   
   private final MapService mapService;
   private final CustomService customService;
   private UserVO loginUserVO;
   private final MapCommentsService mapCommentsService;
//   private final ObjectMapper objectMapper;

   @Autowired
   public CustomController(MapService mapService, CustomService customService, MapCommentsService mapCommentsService) {
	  this.mapCommentsService = mapCommentsService;
      this.mapService = mapService;
      this.customService = customService;
      this.loginUserVO = new UserVO();
      System.out.println("========= customController() 객체생성");
   }
	
   @PostMapping("/saveMap")
   @ResponseBody
   public boolean saveMap(HttpServletRequest request, HttpSession session) throws IOException {
      boolean check = false;
      String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//      session.getAttribute("user");
      System.out.println("커스텀 맵 데이터 받기 성공!!" + jsonString);
      try {
    	  String email = null;
    	  if(isLogin(session)) {
    		  email = loginUserVO.getEmail();
    	  }
         
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
            vo.setCenterLatitude(centerLatitude);
            vo.setCenterLongitude(centerLongitude);
            vo.setViewLevel(level);
            vo.setTitle(title);
            vo.setContent(content);
//            System.out.println("삽입할 map : " + vo);
            
            if( mapService.insertMap(vo) > 0) {
               System.out.println("map 입력 성공!!!");
               check = true;
               ObjectMapper mapper = new ObjectMapper();
               CustomMarker customMarker = mapper.readValue(jsonString, CustomMarker.class);
               //어차피 1개나옴
               MapVO mapVO = mapService.getRecentMap();
               customMarker.setMapIdx(mapVO.getMapIdx());
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
		if(isLogin(session)) {
			mapVO.setOpenYn(null);
			mapVO.setUserEmail(loginUserVO.getEmail());
			List<MapVO> myCustomMapList = mapService.getMapList(mapVO);
			System.out.println("myCustomMapList : " + myCustomMapList.toString());//-------------------test code-----------------
			model.addAttribute("myCustomMapList", myCustomMapList);
		}
		System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
		return "map/customMap"; 
	}
	
	//세션에서 유저 가져옴 + 로그인 여부 확인 메 서드
	private boolean isLogin(HttpSession session) {
		loginUserVO = (UserVO)session.getAttribute("user");
		System.out.println("loginUser : " + loginUserVO);//-------------------test code-----------------
		if(loginUserVO == null || loginUserVO.getEmail() == null) {
			return false;
		} else if("".equals(loginUserVO.getEmail().trim())) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping("/searchCustomMap")
	public String searchMap(MapVO mapVO, Model model) {
		//커스텀맵 검색
		//mapVO.setTitle("관리자");//-------------------test code-----------------
		System.out.println("mapVO : " + mapVO);//-------------------test code-----------------
		List<MapVO> searchMapList = mapService.getSearchMapList(mapVO);
		System.out.println("searchMapList : " + searchMapList.toString());//-------------------test code-----------------
		mapVO.setMapIdx(-1111);
		searchMapList.add(mapVO); //검색키워드 출력용
		model.addAttribute("searchMapList", searchMapList);
		return "map/searchCustomMap"; 
	}
	
	@RequestMapping("/createMap")
	public String ceateMap() {
		//커스텀맵 제작 페이지로 이동
		//로그인 여부 확인 필요, false : 로그인 페이지로 이동
		return "map/createMap"; 
	}
	
	@RequestMapping("/oneCustMap")
	public String oneCustMap(MapVO mapVO, MapCommentsVO mapCommentsVO, Model model) {
		//커스텀맵 하나 상세보기창 이동
		//mapIdx로 불러온 customMapVO 필요
		System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
		mapVO = mapService.getMap(mapVO);
		System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
		model.addAttribute("mapVO", mapVO);
		//커스텀맵에서 사용한 마커종류 리스트 필요
		//표시한 마커목록 리스트 필요
		//댓글리스트 필요
		System.out.println("mapCommentsVO : " + mapCommentsVO);//----------------test code---------------------------
		mapCommentsVO.setMapIdx(String.valueOf(mapVO.getMapIdx()));
		List<MapCommentsVO> mapCommentsList = mapCommentsService.getCommentList(mapCommentsVO);
		System.out.println("mapCommentsList : " + mapCommentsList);//----------------test code---------------------------
        // 모델에 댓글 목록을 추가합니다.
        model.addAttribute("mapCommentsList", mapCommentsList);
		return "map/oneCustMap"; 
	}
	
	@RequestMapping("/updateCustMap")
	public String updateCustMap(HttpServletRequest request, Model model) {
		//커스텀맵 편집페이지 이동
		System.out.println("편집하기!");
		//로그인여부 확인 필요 , false : 로그인 페이지로 이동
		//mapIdx로 불러온 customMapVO 필요
		int MapIdx = Integer.parseInt(request.getParameter("mapIdx"));
		System.out.println(MapIdx);
		Optional<CustomMarker> custom = customService.selectMarker(MapIdx);
		if (custom.isPresent()) {
			System.out.println(">>>> " + custom);
			CustomMarker customMarker = custom.get();
			System.out.println(">>>> " + customMarker);
			model.addAttribute("customMarker", customMarker);
		} else {
			System.out.println("오류!");
			//페이지처리
		}
		//커스텀맵에서 사용한 마커종류 리스트 필요
		//표시한 마커목록 리스트 필요
		return "map/updateCustMap"; 
	}

}

