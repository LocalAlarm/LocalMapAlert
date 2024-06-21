package com.spring.dongnae.user.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.cloudinary.ImageUploadController;
import com.spring.dongnae.user.dao.UserDAO;
import com.spring.dongnae.user.dto.KakaoDTO;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.CustomUserDetails;
import com.spring.dongnae.user.vo.UserVO;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Controller
public class UserController {

   private final UserService userService;
   private final PasswordEncoder passwordEncoder;
   private final JavaMailSender mailSender;
   private final ImageUploadController imageUploadController;
   private final GetAuthenticInfo getAuthenticInfo;

   @Autowired
   public UserController(UserService userService, PasswordEncoder passwordEncoder, JavaMailSender mailSender, ImageUploadController imageUploadController, GetAuthenticInfo getAuthenticInfo) {
      this.userService = userService;
      this.passwordEncoder = passwordEncoder;
      this.mailSender = mailSender;
      this.imageUploadController = imageUploadController;
      this.getAuthenticInfo = getAuthenticInfo;
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
   public String loginError(@ModelAttribute("user") UserVO vo) {
      System.out.println(vo);
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

   // (카카오)로그인후
   @GetMapping("/kakaomain")
   public String kakaoLogin() {
      System.out.println(">>(카카오) 로그인 성공");
      return "user/main";
   }
   
   // 로그인후
   @GetMapping("/main")
   public String main(HttpSession session) {
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null && authentication.isAuthenticated()) {
          String email = getAuthenticInfo.GetEmail();
          System.out.println(">> 로그인 성공 사용자 : " + email);
          CustomUserDetails cud = getAuthenticInfo.GetUser();
          System.out.println("userVO : " + cud);
          cud.setPassword("");
          System.out.println(">> 로그인 성공 사용자정보 : " + cud);
          session.setAttribute("user", cud);
      }
      return "user/profile";
   }

   // 회원가입 페이지로 이동
//   @GetMapping("/joinform")
//   public String joinForm() {
//      System.out.println(">> 회원가입 화면 이동 - joinForm()");
//      return "user/joinform";
//   }
//   
//   @PostMapping("/join")
//   public String join(@ModelAttribute UserVO userVO, 
//            @RequestParam(value = "image", required = false) MultipartFile image) {
//       userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
//       userVO.setToken(passwordEncoder.encode(userVO.getEmail()));
//       System.out.println(">> 회원가입 처리");
//       if (image != null && !image.isEmpty()) { // null 체크 - && !image.isEmpty()
//           Map<String, String> imageMap = imageUploadController.uploadImage(image);
//           userVO.setImagePi(imageMap.get("public_id"));
//           userVO.setImage(imageMap.get("url"));
//       }
//       userVO.setRole("USER");
//       System.out.println(userVO);
//       userService.insertUser(userVO);
//       return "redirect:login";
//   }
   
   // 이메일 중복체크 
   @PostMapping("/checkEmail")
   @ResponseBody
   public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
      System.out.println("email : " + email);
      if (userService.doubleCheckEmail(email) > 0) {
         System.out.println("이메일 중복!!!!");
         return ResponseEntity.ok("duplicate");
      }
      return ResponseEntity.ok("pass");
   }
   
   // 이메일 찾기 
   @RequestMapping("/findEmail")
   public String showFindEmailForm() {
       return "user/findEmail"; 
   }
   
   // 이메일 찾기 결과 
   @PostMapping("/findEmailProcess")
   public String findEmail(HttpServletRequest request, Model model,UserVO vo,
         @RequestParam String nickname, 
         @RequestParam String recoverEmail) {
      try {
         vo.setNickname(nickname);
         vo.setRecoverEmail(recoverEmail); 
         
         String email = userService.findUserEmail(vo);
         
         model.addAttribute("findEmail", email); 

      } catch (Exception e) {
         e.printStackTrace();
      }
      return "user/emailFound";
   }

   //이메일 인증
   @PostMapping("/mailAuthentic")
   @ResponseBody
   public String mailAuthentic(@RequestParam("email") String email) {
      System.out.println("email인증!!!!!!!!! :" + email);
      
      //인증번호만들기
      String num = "0123456789ABCDEFGHIJ";
      StringBuilder authenticNum = new StringBuilder();
      Random random = new Random();
      int length = 6;
      for (int i=0; i<length; i++) {
         int index = random.nextInt(num.length());
         authenticNum.append(num.charAt(index));
      }
      
//      //이메일 보내기
//      String setFrom = "jailju1016@gmail.com";
//      String senderName = "동네한바퀴";
//      String toMail = email;
//      String title = "회원가입 이메일 본인인증";
//      StringBuilder sb = new StringBuilder();
//      sb.append("<html><body>");
//      sb.append("<h1>" + "홈페이지를 방문해주셔서 감사합니다." + "</h1><br><br>");
//      sb.append("인증 번호는 " + authenticNum.toString() + " 입니다.");
//      sb.append("<br>");
//      sb.append("해당 인증번호를 인증번호 확인란에 기입하여 주세요.");
//      sb.append("<html><body>");        
//      sb.append("<html><body>");
//      sb.append("<html><body>");
//      sb.append("</body></html>");
//      String content = sb.toString();
//      
//      try {
//         MimeMessage message = mailSender.createMimeMessage();
//         MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
//         helper.setFrom(new InternetAddress(setFrom, senderName));
//         helper.setTo(toMail);
//         helper.setSubject(title);
//         helper.setText(content, true);
//         mailSender.send(message);
//
//          // 첨부 파일 추가
////         FileSystemResource file = new FileSystemResource(new File());
////         helper.addAttachment(, file);
//           
//      } catch (MessagingException | UnsupportedEncodingException e) {
//         e.printStackTrace();
//      }
      
      return authenticNum.toString();
   }
   
   // 비밀번호 찾기
   @RequestMapping("/findPassword")
   public String showFindPasswordForm(@RequestParam(value = "profile", required = false) String profile, Model model) {
      System.out.println("location : " + profile);
      model.addAttribute("profile", profile);
       return "user/findPassword"; 
   }

   @PostMapping("/findEmailForPassword")
   @ResponseBody
   public String findEmail(@RequestParam("email") String email) {
      String findEmail = userService.findPasswordByEmail(email);
      return findEmail;
   }
   
   @PostMapping("/passwordChange")
   public String passwordChange(@ModelAttribute UserVO userVO, @RequestParam(value = "profile", required = false) String profile) {
      System.out.println("비번바꾸기 처리");
      userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
      System.out.println("바꾸기 vo : " + userVO);
      String redirectURL = "redirect:login";
       if (profile != null) {
          //프로필에서 온거 구분 하지만 비번바꾸면 로그아웃되고 다시 로그인?
//           redirectURL += ;
       }
      userService.updatePassowrd(userVO);
      return redirectURL;
   }
   
   @GetMapping("/profile")
   public String profile(HttpSession session) {
      UserVO userVO = (UserVO) session.getAttribute("user");
      System.out.println("프로필vo : " + userVO);
      return "user/profile";
   }
   
   @PostMapping("/updateProfile")
   @ResponseBody
   public void updateProfile(@RequestParam(value = "email", required = false) String email
         , @RequestParam(value = "idx", required = false) String idxS
         , @RequestParam(value = "newValue", required = false) String newValue
         , @RequestParam(value = "address", required = false) String address
         , @RequestParam(value = "detailAddress", required = false) String detailAddress
         , @RequestParam(value = "image", required = false) MultipartFile image) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("email", email);
      int idx = Integer.parseInt(idxS);
      map.put("idx", idx);
      System.out.println("프로필 수정처리 : " + map);
      System.out.println("email : " + email);
      if (idx == 5 && image != null && !image.isEmpty()) {
           Map<String, String> imageMap = imageUploadController.uploadImage(image);
           map.put("image", imageMap.get("url"));
           map.put("imagePi", imageMap.get("public_id"));
       }
      else if (idx == 1) {
         map.put("address", address);
         map.put("detailAddress", detailAddress);
      } else {
         map.put("newValue", newValue);
      }

      System.out.println("프로필 수정처리>> : " + map);
      userService.updateProfile(map);
   }
}


