package com.spring.dongnae.map.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dongnae.user.vo.UserVO;

@Controller
public class MapController {
	@RequestMapping("/map")
    public String map(HttpSession session) {
        return "map/map"; 
    }

	
    @RequestMapping(value = "/userAddress", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getUserAddress(HttpSession session) {
        UserVO userVO = (UserVO) session.getAttribute("user");
        
        if (userVO != null) {
            String address = userVO.getAddress();
            
            return "{\"address\": \"" + address + "\"}";
        } else {
            // 사용자 정보가 없을때/
            return "{\"address\": \"없음\"}";
        }
    }
	
	@RequestMapping("/customMap")
	public String customMap() {
		//커스텀 맵 불러오는 창
		//커스텀맵 +제작자 나 리스트로 불러옴
		//다른사람이 만든 커스텀 맵 불러옴 필요한가?
		return "map/customMap"; 
	}
	
	
}
