package com.spring.dongnae.utils.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.spring.dongnae.user.vo.CustomUserDetails;
import com.spring.dongnae.user.vo.UserVO;

@Component
public class GetAuthenticInfo {
	
	private Authentication authentication = null;
	
	public String GetToken() {
		try {			
			authentication = SecurityContextHolder.getContext().getAuthentication();
			return ((CustomUserDetails) authentication.getPrincipal()).getToken();
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public String GetEmail() {
		try {			
			authentication = SecurityContextHolder.getContext().getAuthentication();
//			 SecurityContextHolder.getContext().setAuthentication(authentication);
			return ((CustomUserDetails) authentication.getPrincipal()).getUsername();
		} catch(NullPointerException e) {
			return null;
		}
	}
	public CustomUserDetails GetLoginUser() {
		try {			
			authentication = SecurityContextHolder.getContext().getAuthentication();
			return ((CustomUserDetails) authentication.getPrincipal());
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public CustomUserDetails GetUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		System.out.println((CustomUserDetails) authentication.getPrincipal());
		return (CustomUserDetails) authentication.getPrincipal();
	}
}
