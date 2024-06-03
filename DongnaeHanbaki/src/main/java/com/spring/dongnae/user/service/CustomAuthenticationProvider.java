package com.spring.dongnae.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.spring.dongnae.user.vo.UserVO;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authentication 작동");
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserVO userVO = userService.getUserByEmail(email);
        
        if (userVO == null) {
            throw new BadCredentialsException("Invalid Email or Password");
        }
        
        // 입력된 비밀번호 인코딩 후 출력
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("인코딩된 입력 비밀번호: " + encodedPassword);

        if (!passwordEncoder.matches(password, userVO.getPassword())) {
        	System.out.println("match가 안된다!!");
            throw new BadCredentialsException("Invalid Email or Password");
        }

        return new UsernamePasswordAuthenticationToken(userVO, password, userVO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
