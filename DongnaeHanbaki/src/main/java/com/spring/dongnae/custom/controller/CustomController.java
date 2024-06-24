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
      System.out.println("========= customController() 媛앹껜�깮�꽦");
   }
   
   @PostMapping("/saveMap")
   @ResponseBody
   public boolean saveMap(HttpServletRequest request, HttpSession session) throws IOException {
      boolean check = false;
      int insertCheck = 0;
      int updateCheck = 0;
      String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//      session.getAttribute("user");
      System.out.println("而ㅼ뒪�� 留� �뜲�씠�꽣 諛쏄린 �꽦怨�!!" + jsonString);
      String email = getAuthenticInfo.GetEmail();
      System.out.println("email : " + email);
      try {
         
         ObjectMapper objectMapper = new ObjectMapper();
         
            // JSON 臾몄옄�뿴�쓣 JsonNode 媛앹껜濡� 蹂��솚
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // center �븘�뱶 異붿텧
            String center = jsonNode.get("center").asText();
            
            // 愿꾪샇 �젣嫄� �썑 遺꾨━
            String coordinates = center.substring(1, center.length() - 1);
            String[] parts = coordinates.split(", ");
            
         // 醫뚰몴 異붿텧
            double la = Double.parseDouble(parts[0]);
            double ma = Double.parseDouble(parts[1]);

            // 蹂��닔�뿉 ���옣
            double centerLongitude = la;
            double centerLatitude = ma;
            // level �븘�뱶 異붿텧
            String level = jsonNode.get("level").asText();
            String title = jsonNode.get("title").asText();
            String content = jsonNode.get("content").asText();
            String address = jsonNode.get("address").asText();
            String openYn = jsonNode.has("openYn") ? jsonNode.get("openYn").asText() : "0";
            int mapIdx = jsonNode.has("mapIdx") ? jsonNode.get("mapIdx").asInt() : -1;
            // 異붿텧�븳 �븘�뱶 媛� 異쒕젰
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
                // mapIdx媛� 議댁옱�븯�뒗 寃쎌슦
                vo.setMapIdx(mapIdx);
                updateCheck = mapService.updateMap(vo);
            } else {
                // mapIdx媛� 議댁옱�븯吏� �븡�뒗 寃쎌슦
            	insertCheck = mapService.insertMap(vo);
                System.out.println("mapIdx �븘�뱶媛� 議댁옱�븯吏� �븡�뒿�땲�떎.");
            }
            System.out.println("�궫�엯�븷 map : " + vo);
            if(insertCheck > 0) {   
               System.out.println("map �엯�젰 �꽦怨�!!!");
               check = true;
               ObjectMapper mapper = new ObjectMapper();
               CustomMarker customMarker = mapper.readValue(jsonString, CustomMarker.class);
               //�뼱李⑦뵾 1媛쒕굹�샂
               MapVO mapVO = mapService.getRecentMap();
               customMarker.setMapIdx(mapVO.getMapIdx());
               System.out.println("而ㅼ뒪�� �뒪�궎留덈줈 蹂�寃�! : " + customMarker);
               customService.saveMarker(customMarker);
            } else if (updateCheck > 0) {
            	System.out.println("map �엯�젰 �꽦怨�!!!");
                check = true;
                ObjectMapper mapper = new ObjectMapper();
                CustomMarker customMarker = mapper.readValue(jsonString, CustomMarker.class);
                //�뼱李⑦뵾 1媛쒕굹�샂
                customMarker.setMapIdx(mapIdx);
                System.out.println("而ㅼ뒪�� �뒪�궎留덈줈 蹂�寃�! : " + customMarker);
                customService.saveMarker(customMarker);
            }
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
      return check;
   }
   
   @RequestMapping("/customMap")
   public String customMap(MapVO mapVO, Model model,HttpSession session) {
      //而ㅼ뒪�� 留� 遺덈윭�삤�뒗 李�
      //怨듦컻�맂寃� 寃��깋
      mapVO.setOpenYn("1");
      System.out.println("mapVO : " + mapVO);//-------------------test code-----------------
      //�떎瑜몄궗�엺�씠 留뚮뱺 而ㅼ뒪�� 留� 以� 怨듦컻�맂寃� 紐⑸줉 遺덈윭�샂
      List<MapVO> openCustomMapList = mapService.getMapList(mapVO);
      System.out.println("openCustomMapList : " + openCustomMapList.toString());//-------------------test code-----------------
      model.addAttribute("openCustomMapList", openCustomMapList);
      //濡쒓렇�씤 �뿬遺� �솗�씤-> true : �궡 而ㅼ뒪�� 留� 遺덈윭�샂
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
   
//   //�꽭�뀡�뿉�꽌 �쑀�� 媛��졇�샂 + 濡쒓렇�씤 �뿬遺� �솗�씤 硫� �꽌�뱶
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
      //而ㅼ뒪��留� 寃��깋
//      mapVO.setTitle("111");//-------------------test code-----------------
      System.out.println("map : " + mapVO);
      List<MapVO> serchMapList = mapService.getSearchMapList(mapVO);//-------------------test code-----------------
      System.out.println("serchMapList : " + serchMapList.toString());//-------------------test code-----------------
      model.addAttribute("serchMapList", serchMapList);//-------------------test code-----------------
      return "map/serchCustomMap"; 
   }
   
   @RequestMapping("/createMap")
   public String ceateMap() {
      //而ㅼ뒪��留� �젣�옉 �럹�씠吏�濡� �씠�룞
      //濡쒓렇�씤 �뿬遺� �솗�씤 �븘�슂, false : 濡쒓렇�씤 �럹�씠吏�濡� �씠�룞
      return "map/createMap"; 
   }
   
   @RequestMapping("/oneCustMap")
   public String oneCustMap(MapVO mapVO, MapCommentsVO mapCommentsVO, Model model) {
      //而ㅼ뒪��留� �븯�굹 �긽�꽭蹂닿린李� �씠�룞
      //mapIdx濡� 遺덈윭�삩 customMapVO �븘�슂
      System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
      mapVO = mapService.getMap(mapVO);
      System.out.println("mapVO : " + mapVO);//----------------test code---------------------------
      model.addAttribute("mapVO", mapVO);
      //而ㅼ뒪��留듭뿉�꽌 �궗�슜�븳 留덉빱醫낅쪟 由ъ뒪�듃 �븘�슂
      //�몴�떆�븳 留덉빱紐⑸줉 由ъ뒪�듃 �븘�슂
      //�뙎湲�由ъ뒪�듃 �븘�슂
      System.out.println("mapCommentsVO : " + mapCommentsVO);//----------------test code---------------------------
      mapCommentsVO.setMapIdx(String.valueOf(mapVO.getMapIdx()));
      List<MapCommentsVO> mapCommentsList = mapCommentsService.getCommentList(mapCommentsVO);
      System.out.println("mapCommentsList : " + mapCommentsList);//----------------test code---------------------------
        // 紐⑤뜽�뿉 �뙎湲� 紐⑸줉�쓣 異붽��빀�땲�떎.
        model.addAttribute("mapCommentsList", mapCommentsList);
      return "map/oneCustMap"; 
   }
   
   @RequestMapping("/updateCustMap")
   public String updateCustMap(HttpServletRequest request, Model model) throws JsonProcessingException {
      //而ㅼ뒪��留� �렪吏묓럹�씠吏� �씠�룞
      System.out.println("�렪吏묓븯湲�!");
      //濡쒓렇�씤�뿬遺� �솗�씤 �븘�슂 , false : 濡쒓렇�씤 �럹�씠吏�濡� �씠�룞
      //mapIdx濡� 遺덈윭�삩 customMapVO �븘�슂
      int MapIdx = Integer.parseInt(request.getParameter("mapIdx"));
      System.out.println(MapIdx);
      //而ㅼ뒪��留듭뿉�꽌 �궗�슜�븳 留덉빱醫낅쪟 由ъ뒪�듃 �븘�슂
      //�몴�떆�븳 留덉빱紐⑸줉 由ъ뒪�듃 �븘�슂
      return "map/updateCustMap"; 
   }
   
   @RequestMapping(value = "/allMarker", method=RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<?> selectAllMarker(@RequestParam("mapIdx") String mapIdx) {
        try {
            System.out.println("留덉빱 �뜲�씠�꽣 媛��졇�삤湲�!");
            System.out.println("mapIdx: " + mapIdx);
            int idx = Integer.parseInt(mapIdx); // 臾몄옄�뿴�쓣 �젙�닔濡� 蹂��솚
            Optional<CustomMarker> custom = customService.selectMarker(idx);
            if (custom.isPresent()) {
                CustomMarker customMarker = custom.get();
                System.out.println(">>>> " + customMarker);
                return ResponseEntity.ok(customMarker);
            } else {
                System.out.println("�삤瑜�: �빐�떦 mapIdx�뿉 �빐�떦�븯�뒗 �뜲�씠�꽣媛� �뾾�뒿�땲�떎.");
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            System.out.println("�삤瑜�: mapIdx �뙆�씪誘명꽣媛� �젙�닔濡� 蹂��솚�븷 �닔 �뾾�뒿�땲�떎.");
            return ResponseEntity.badRequest().body("Invalid mapIdx parameter: " + mapIdx);
        } catch (Exception e) {
            System.out.println("�삤瑜�: �꽌踰꾩뿉�꽌 �뜲�씠�꽣 議고쉶 以� �삁�쇅 諛쒖깮");
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

}

