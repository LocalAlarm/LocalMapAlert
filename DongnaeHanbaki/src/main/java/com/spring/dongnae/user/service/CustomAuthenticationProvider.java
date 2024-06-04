//package com.spring.dongnae.user.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.spring.dongnae.user.vo.CustomUserDetails;
//import com.spring.dongnae.user.vo.UserVO;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        System.out.println("Authentication 작동");
//        String email = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        UserVO formUserVO = new UserVO();
//        formUserVO.setEmail(email);
//        formUserVO.setPassword(password);
//
//        UserVO userVO = userService.getUser(formUserVO);
//
//        if (userVO == null) {
//        	System.out.println("유저vo가 null값!");
//            throw new BadCredentialsException("Invalid Email or Password");
//        }
//
//        if (!passwordEncoder.matches(password, userVO.getPassword())) {
//        	System.out.println("match가 안된다!!");
//            throw new BadCredentialsException("Invalid Email or Password");
//        }
//
//        return new UsernamePasswordAuthenticationToken(userVO, password, CustomUserDetails.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
