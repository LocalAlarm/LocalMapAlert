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
import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.FriendRoom;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Controller
public class JoinController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final ImageUploadController imageUploadController;
	private final UserRoomsRepository userRoomsRepository;
	
	@Autowired
	public JoinController(UserService userService, PasswordEncoder passwordEncoder, ImageUploadController imageUploadController, UserRoomsRepository userRoomsRepository) {
		this.userService = userService;
	    this.passwordEncoder = passwordEncoder;
	    this.imageUploadController = imageUploadController;
		this.userRoomsRepository = userRoomsRepository;
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
	       UserRooms userRooms = new UserRooms(userVO);
	       userRoomsRepository.save(userRooms);
	       userVO.setToken(userRooms.getId());
	       System.out.println(">> 회원가입 처리");
	       if (image != null && !image.isEmpty()) { // null 체크 - && !image.isEmpty()
	           Map<String, String> imageMap = imageUploadController.uploadImage(image);
	           userVO.setImagePi(imageMap.get("public_id"));
	           userVO.setImage(imageMap.get("url"));
	       } else {
	    	   userVO.setImagePi(null);
	    	   userVO.setImage("https://res.cloudinary.com/djlee4yl2/image/upload/v1713834954/logo/github_logo_icon_tpisfg.png");
	       }
	       userVO.setRole("USER");
	       System.out.println(userVO);
	       int insertCheck = userService.insertUser(userVO);
	       System.out.println("insertCheck : " + insertCheck);
	       if (insertCheck > 0) {
	           return ResponseEntity.ok("pass");
	       } else {
	    	   userRoomsRepository.delete(userRooms);
	           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입에 실패했습니다.");
	       }
	   }

}
