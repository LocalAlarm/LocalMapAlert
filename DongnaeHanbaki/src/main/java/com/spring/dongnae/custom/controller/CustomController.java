package com.spring.dongnae.custom.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomMapEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dongnae.bbs.MapCommentsService;
import com.spring.dongnae.bbs.MapService;
import com.spring.dongnae.bbs.MapVO;
import com.spring.dongnae.bbs.MarkerService;
import com.spring.dongnae.user.vo.UserVO;

@Controller
public class CustomController {

	public CustomController() {
		System.out.println("========= CustomController() 객체생성");
	}
	@Autowired
	MapService mapService;
	@Autowired
	MarkerService markerService;
	@Autowired
	MapCommentsService mapCommentsService;
	
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
	
	@PostMapping("/saveMap")
	@ResponseBody
	public boolean saveMap(HttpServletRequest request) throws IOException {
		boolean check = false;
		String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("커스텀 맵 데이터 받기 성공!!" + jsonString);
		return check;
	}
}
