package com.spring.dongnae.map.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.dongnae.user.vo.UserVO;

@Controller
public class MapController {
	@RequestMapping("/map")
    public String map(HttpSession session) {
		UserVO userVO = (UserVO) session.getAttribute("user");
		System.out.println(userVO.getAddress());
        return "map/map"; 
    }
	
	@RequestMapping("/customMap")
	public String customMap() {
		//커스텀 맵 불러오는 창
		//커스텀맵 +제작자 나 리스트로 불러옴
		//다른사람이 만든 커스텀 맵 불러옴 필요한가?
		return "map/customMap"; 
	}
	
	
}
