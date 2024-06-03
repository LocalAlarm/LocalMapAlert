package com.spring.dongnae.user.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		System.out.println("========= UserController() 객체생성");
	}

	@GetMapping("/login")
	public String loginView(@ModelAttribute("user") UserVO vo) {
		System.out.println(">> 로그인 화면이동 - loginView()");
		return "user/login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println(">> 로그아웃 처리");
		session.invalidate();

		return "user/login";
	}

	
	@RequestMapping("/loginerror")
	public String loginError() {
		System.out.println(">> 로그인 에러");
		return "user/loginerror";
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String kakaoRedirect(@RequestParam("code") String code, @RequestParam("state") String state,
			HttpSession session) {
		System.out.println(">> 카카오 로그인 리디렉션 처리 - code: " + code + ", state: " + state);
		return "user/redirect";
	}

	// kakao로그인 data 컨트롤
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

			// vo에맞게 세팅
			UserVO userVO = new UserVO();
			userVO.setEmail(kakaoDTO.getEmail());
			userVO.setNickname(kakaoDTO.getNickname());
			userVO.setImage(kakaoDTO.getThumbnailImageUrl());
			userVO.setKakaoCheck(1);
			System.out.println("vo변형성공!!!!!!!!!!!!!!!!!!!!!! " + userVO);

			// 데이터베이스에서 조회후 없으면 데이터베이스에 insert
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

	// 카카오로그인후
	@GetMapping("/main")
	public String kakaoLogin() {
		System.out.println(">>카카오 로그인 성공");
		return "user/main";
	}

	// 회원가입 페이지로 이동 - 건희
	@GetMapping("/joinform")
	public String joinForm() {
		System.out.println(">> 회원가입 화면 이동 - joinForm()");
		return "user/joinform";
	}

	@PostMapping("/join")
	public String join(@ModelAttribute UserVO userVO) {
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		System.out.println(">> 회원가입 처리");
		userService.insertUser(userVO);
		return "redirect:login";
	}
	
	//이메일 중복체크 - 건희
		 @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
		 @ResponseBody
		 public String checkEmail(@RequestParam("email") String email) {
			 	System.out.println("email : " + email);
		        return userService.doubleCheckEmail(email);
		 }
		 
		// 회원가입 완료 페이지로 이동 - 건희
		// 회원가입 완료 페이지로 이동 및 회원가입 처리 - 건희
		    @RequestMapping(value = "/joinOk", method = RequestMethod.POST)
		    public String joinOk(HttpServletRequest request, @RequestParam("image") MultipartFile image, Model model) {
		        System.out.println(">> 회원가입 완료 처리 - joinOk()");
		        
		        String email = request.getParameter("email");
		        String password = request.getParameter("password");
		        String nickname = request.getParameter("nickname");
		        String address = request.getParameter("address");
		        String detailAddress = request.getParameter("detailAddress");
		        String recoverEmail = request.getParameter("recoverEmail");

		        UserVO user = new UserVO();
		        user.setEmail(email);
		        user.setPassword(password);
		        user.setNickname(nickname);
		        user.setAddress(address);
		        user.setDetailAddress(detailAddress);
		        user.setRecoverEmail(recoverEmail);
		        
		        // 파일 업로드 처리
		        if (!image.isEmpty()) {
		            try {
		                byte[] bytes = image.getBytes();
		                // 원하는 파일 저장 위치에 저장하는 로직 추가
		                // 예시: Files.write(Paths.get("path/to/save/" + image.getOriginalFilename()), bytes);
		                user.setImage(image.getOriginalFilename());
		            } catch (Exception e) {
		                e.printStackTrace();
		                model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
		                return "user/joinOk";
		            }
		        }

		        try {
		            userService.insertUser(user);
		            model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
		            return "user/joinOk";
		        } catch (Exception e) {
		            e.printStackTrace();
		            model.addAttribute("message", "회원가입 중 오류가 발생했습니다.");
		            return "user/joinOk";
		        }
		    }

}
