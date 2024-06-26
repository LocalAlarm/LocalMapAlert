package com.spring.dongnae.custom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.bbs.MapCommentsService;
import com.spring.dongnae.bbs.MapCommentsVO;
import com.spring.dongnae.custom.scheme.CustomMarker;
import com.spring.dongnae.custom.service.CustomService;
import com.spring.dongnae.map.service.MapService;
import com.spring.dongnae.map.vo.MapVO;
import com.spring.dongnae.user.vo.CustomUserDetails;
import com.spring.dongnae.user.vo.UserVO;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Controller
public class CustomController {
   
   private final MapService mapService;
   private final CustomService customService;
   private UserVO loginUserVO;
   private final MapCommentsService mapCommentsService;
   private final GetAuthenticInfo getAuthenticInfo;
//   private final ObjectMapper objectMapper;

   @Autowired
   public CustomController(MapService mapService, CustomService customService, MapCommentsService mapCommentsService, GetAuthenticInfo getAuthenticInfo) {
     this.mapCommentsService = mapCommentsService;
      this.mapService = mapService;
      this.customService = customService;
      this.loginUserVO = new UserVO();
      this.getAuthenticInfo = getAuthenticInfo;
      System.out.println("========= customController() 객체생성");
   }
   
   @PostMapping("/saveMap")
   @ResponseBody
   public boolean saveMap(HttpServletRequest request, HttpSession session) throws IOException {
      boolean check = false;
      int insertCheck = 0;
      int updateCheck = 0;
      String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//      session.getAttribute("user");
      System.out.println("커스텀 맵 데이터 받기 성공!!" + jsonString);
      String email = getAuthenticInfo.GetEmail();
      System.out.println("email : " + email);
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
            String address = jsonNode.get("address").asText();
            String openYn = jsonNode.has("openYn") ? jsonNode.get("openYn").asText() : "0";
            int mapIdx = jsonNode.has("mapIdx") ? jsonNode.get("mapIdx").asInt() : -1;
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
            vo.setOpenYn(openYn);
            if (mapIdx != -1) {
                // mapIdx가 존재하는 경우
                vo.setMapIdx(mapIdx);
                updateCheck = mapService.updateMap(vo);
            } else {
                // mapIdx가 존재하지 않는 경우
            	insertCheck = mapService.insertMap(vo);
                System.out.println("mapIdx 필드가 존재하지 않습니다.");
            }
            System.out.println("삽입할 map : " + vo);
            if(insertCheck > 0) {   
               System.out.println("map 입력 성공!!!");
               check = true;
               ObjectMapper mapper = new ObjectMapper();
               CustomMarker customMarker = mapper.readValue(jsonString, CustomMarker.class);
               //어차피 1개나옴
               MapVO mapVO = mapService.getRecentMap();
               customMarker.setMapIdx(mapVO.getMapIdx());
               System.out.println("커스텀 스키마로 변경! : " + customMarker);
               customService.saveMarker(customMarker);
            } else if (updateCheck > 0) {
            	System.out.println("map 입력 성공!!!");
                check = true;
                ObjectMapper mapper = new ObjectMapper();
                CustomMarker customMarker = mapper.readValue(jsonString, CustomMarker.class);
                //어차피 1개나옴
                customMarker.setMapIdx(mapIdx);
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
      String email = getAuthenticInfo.GetEmail();
//      CustomUserDetails cud = getAuthenticInfo.GetUser();
//      System.out.println(">>> uservo값 : " + cud);
      if(email != null) {
         mapVO.setOpenYn(null);
         mapVO.setUserEmail(email);
         List<MapVO> myCustomMapList = mapService.getMapList(mapVO);
         System.out.println("myCustomMapList : " + myCustomMapList.toString());//-------------------test code-----------------
         model.addAttribute("myCustomMapList", myCustomMapList);
         model.addAttribute("user", email);
      }
      System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
      return "map/customMap"; 
   }
   
//   //세션에서 유저 가져옴 + 로그인 여부 확인 메 서드
//   private boolean isLogin(HttpSession session) {
//      loginUserVO = (UserVO)session.getAttribute("user");
//      System.out.println("loginUser : " + loginUserVO);//-------------------test code-----------------
//      if(loginUserVO == null || loginUserVO.getEmail() == null) {
//         return false;
//      } else if("".equals(loginUserVO.getEmail().trim())) {
//         return false;
//      } else {
//         return true;
//      }
//   }
   
   @RequestMapping("/serchCustomMap")
   public String serchMap(MapVO mapVO, Model model) {
      //커스텀맵 검색
//      mapVO.setTitle("111");//-------------------test code-----------------
      System.out.println("map : " + mapVO);
      List<MapVO> serchMapList = mapService.getSearchMapList(mapVO);//-------------------test code-----------------
      System.out.println("serchMapList : " + serchMapList.toString());//-------------------test code-----------------
      model.addAttribute("serchMapList", serchMapList);//-------------------test code-----------------
      return "map/serchCustomMap"; 
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
   public String updateCustMap(HttpServletRequest request, Model model){
      //커스텀맵 편집페이지 이동
      System.out.println("편집하기!");
      //로그인여부 확인 필요 , false : 로그인 페이지로 이동
      //mapIdx로 불러온 customMapVO 필요
      int MapIdx = Integer.parseInt(request.getParameter("mapIdx"));
      System.out.println(MapIdx);
      //커스텀맵에서 사용한 마커종류 리스트 필요
      //표시한 마커목록 리스트 필요
      return "map/updateCustMap"; 
   }
   
   @RequestMapping(value = "/allMarker", method=RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<?> selectAllMarker(@RequestParam("mapIdx") String mapIdx) {
        try {
            System.out.println("마커 데이터 가져오기!");
            System.out.println("mapIdx: " + mapIdx);
            int idx = Integer.parseInt(mapIdx); // 문자열을 정수로 변환
            Optional<CustomMarker> custom = customService.selectMarker(idx);
            if (custom.isPresent()) {
                CustomMarker customMarker = custom.get();
                System.out.println(">>>> " + customMarker);
                return ResponseEntity.ok(customMarker);
            } else {
                System.out.println("오류: 해당 mapIdx에 해당하는 데이터가 없습니다.");
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            System.out.println("오류: mapIdx 파라미터가 정수로 변환할 수 없습니다.");
            return ResponseEntity.badRequest().body("Invalid mapIdx parameter: " + mapIdx);
        } catch (Exception e) {
            System.out.println("오류: 서버에서 데이터 조회 중 예외 발생");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   
   @RequestMapping("/deleteCustMap")
   public String deleteMap(MapVO mapVO) {
	   mapVO = mapService.getMap(mapVO);
	   if(mapVO.getUserEmail().equals(getAuthenticInfo.GetEmail())) {
		   mapService.deleteMap(mapVO);
	   }
	   return "redirect:/customMap";
   }
   
   @RequestMapping("/updateOpenYn")
   public String updateOpenYn(@RequestParam("mapIdx") String mapIdx, @RequestParam("openYn") String openYn) {
       System.out.println("mapIdx: " + mapIdx);
       System.out.println("openYn: " + openYn);
       MapVO mapVO = new MapVO();
	   int idx = Integer.parseInt(mapIdx);
	   mapVO.setMapIdx(idx);
	   mapVO.setOpenYn(openYn);
	   System.out.println("mapvo : " + mapVO);
	   mapVO = mapService.getMap(mapVO);
       
       // 사용자 인증 정보에 따라 업데이트를 수행합니다.
	   if(mapVO.getUserEmail().equals(getAuthenticInfo.GetEmail())) {
		   mapService.updateMap(mapVO);
	   } else {
           System.out.println("사용자 인증 정보와 일치하지 않습니다.");
       }

       return "redirect:/customMap";
   }

}
