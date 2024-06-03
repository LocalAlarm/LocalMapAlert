package com.spring.dongnae.user.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.user.dto.KakaoDTO;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Controller
public class UserController {
	
    @Autowired
    private UserService userService;

    public UserController() {
        System.out.println("========= UserController() 객체생성");
    }

//    @PostMapping("/loginProc")
//    public String login(UserVO vo) throws Exception {
//        System.out.println(">> 로그인 처리");
//        System.out.println("vo : " + vo);
//
//        if (vo.getEmail() == null || vo.getEmail().equals("")) {
//            throw new IllegalArgumentException("아이디는 반드시 입력해야 합니다");
//        }
//        
//        UserVO user = userService.getUser(vo);
//        System.out.println("user : " + user);
//        
//        if (user != null) {
//            System.out.println(">>로그인 성공");
//            return "user/main"; 
//        } else {
//            System.out.println(">>로그인 실패~~~");
//            return "user/login"; 
//        }
//    }

    @GetMapping("/login")
    public String loginView(@ModelAttribute("user") UserVO vo) {
        System.out.println(">> 로그인 화면이동 - loginView()");
        vo.setEmail("test1@naver.com");
        vo.setPassword("test1");
        return "user/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println(">> 로그아웃 처리");
        session.invalidate();
        
        return "user/login";
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String kakaoRedirect(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        System.out.println(">> 카카오 로그인 리디렉션 처리 - code: " + code + ", state: " + state);
        return "user/redirect";
    }

    //kakao로그인 data 컨트롤
    @RequestMapping(value = "/kakaoData", method = RequestMethod.POST)
    @ResponseBody
    public boolean KaKaoToMain(HttpServletRequest request) {
        boolean check = false;
        try {
            // 클라이언트가 보낸 JSON 데이터를 문자열로 받음
            String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("받는거 성공!!!!!!!!!!!!!!!!!!!! " + jsonString);
            // jsonString kakaoDTO로 변형
            ObjectMapper mapper = new ObjectMapper();
            KakaoDTO kakaoDTO = mapper.readValue(jsonString, KakaoDTO.class);
            System.out.println("dto변형성공!!!!!!!!!!!!!!!!!!!!!! " + kakaoDTO);
            
            //vo에맞게 세팅
            UserVO userVO = new UserVO();
            userVO.setEmail(kakaoDTO.getEmail());
            userVO.setNickname(kakaoDTO.getNickname());
            userVO.setImage(kakaoDTO.getThumbnailImageUrl());
            userVO.setKakaoCheck(1);
            System.out.println("vo변형성공!!!!!!!!!!!!!!!!!!!!!! " + userVO);
            
            //데이터베이스에서 조회후 없으면 데이터베이스에 insert
            UserVO checkVO = userService.getUser(userVO);
            System.out.println("있는지체크!!!!!!!!!!!! " + checkVO);
            if (checkVO == null) {
            	userService.insertKakaoUser(userVO);
            	System.out.println("데이터베이스insert!!!!!!!!!!!!!!!");
            }
            check = true; // 처리 성공 시 true 반환
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    
    //카카오로그인후
    @RequestMapping(value = "/main")
    public String kakaoLogin () {
    	System.out.println(">>카카오 로그인 성공");
        return "user/main";
    }
  
  // 회원가입 페이지로 이동 - 건희
	@RequestMapping("/join")
	   public String join() {
	       System.out.println(">> 회원가입 화면 이동 - join()");
	       return "user/join";
	   }
  
}
