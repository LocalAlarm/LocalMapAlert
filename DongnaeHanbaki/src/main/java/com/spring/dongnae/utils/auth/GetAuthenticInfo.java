package com.spring.dongnae.utils.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.spring.dongnae.user.vo.CustomUserDetails;

@Component
public class GetAuthenticInfo {
	
	private Authentication authentication = null;
	
	public String GetToken() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((CustomUserDetails) authentication.getPrincipal()).getToken();
	}
	
	public String GetEmail() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((CustomUserDetails) authentication.getPrincipal()).getUsername();
	}
	
}
