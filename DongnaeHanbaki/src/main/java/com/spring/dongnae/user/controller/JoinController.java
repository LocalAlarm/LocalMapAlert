package com.spring.dongnae.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Controller
public class JoinController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final ImageUploadController imageUploadController;
	
	@Autowired
	public JoinController(UserService userService, PasswordEncoder passwordEncoder, ImageUploadController imageUploadController) {
		this.userService = userService;
	    this.passwordEncoder = passwordEncoder;
	    this.imageUploadController = imageUploadController;
	    System.out.println("========= JoinController() 객체생성");
	 }
	
	// 회원가입 페이지로 이동
	   @GetMapping("/joinform")
	   public String joinForm() {
	      System.out.println(">> 회원가입 화면 이동 - joinForm()");
	      return "user/joinform";
	   }
	   
	   @PostMapping("/join")
	   @ResponseBody
	   public ResponseEntity<String> join(@ModelAttribute UserVO userVO, 
	                                      @RequestParam(value = "image", required = false) MultipartFile image) {
	       userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
	       userVO.setToken(passwordEncoder.encode(userVO.getEmail()));
	       System.out.println(">> 회원가입 처리");
	       if (image != null && !image.isEmpty()) { // null 체크 - && !image.isEmpty()
	           Map<String, String> imageMap = imageUploadController.uploadImage(image);
	           userVO.setImagePi(imageMap.get("public_id"));
	           userVO.setImage(imageMap.get("url"));
	       }
	       userVO.setRole("USER");
	       System.out.println(userVO);
	       int insertCheck = userService.insertUser(userVO);
	       System.out.println("insertCheck : " + insertCheck);
	       if (insertCheck > 0) {
	           return ResponseEntity.ok("pass");
	       } else {
	           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입에 실패했습니다.");
	       }
	   }

}
