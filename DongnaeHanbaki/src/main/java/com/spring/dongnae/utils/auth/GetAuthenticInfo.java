package com.spring.dongnae.utils.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.spring.dongnae.user.vo.CustomUserDetails;

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
			return ((CustomUserDetails) authentication.getPrincipal()).getUsername();
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public String GetImg() {
		try {
			authentication = SecurityContextHolder.getContext().getAuthentication();
			return ((CustomUserDetails) authentication.getPrincipal()).getImage();
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public CustomUserDetails GetCUD() {
		try {
			authentication = SecurityContextHolder.getContext().getAuthentication();
			return ((CustomUserDetails) authentication.getPrincipal());
		} catch (Exception e) {
			return null;
		}
	}
}
