package com.spring.dongnae.utils.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.spring.dongnae.user.vo.CustomUserDetails;
import com.spring.dongnae.user.vo.UserVO;

@Component
public class SetAuthenticInfo {
	
	private Authentication authentication = null;
	
	public void setUser(UserVO vo) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		System.out.println((CustomUserDetails) authentication.getPrincipal());
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetails.setCustomUserDetails(vo.getEmail(), vo.getPassword(), vo.getToken(), vo.getImage(), vo.getNickname()
        														, vo.getAddress(), vo.getDetailAddress(), vo.getEmail(), vo.getRecoverEmail(), vo.getImagePi(), vo.getKakaoCheck()
        														, vo.getRole());
        
    }
	
}
