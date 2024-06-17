package com.spring.dongnae.user.service;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dongnae.user.dao.UserDAO;
import com.spring.dongnae.user.vo.CustomUserDetails;
import com.spring.dongnae.user.vo.UserVO;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserVO userVO = userDAO.getIdUser(userId);
		if (userVO == null) {
		    throw new UsernameNotFoundException("사용자가 입력한 아이디에 해당하는 사용자를 찾을 수 없습니다.");
		}
		
		CustomUserDetails cud = new CustomUserDetails();
		cud.setUsername(userVO.getEmail());
		cud.setPassword(userVO.getPassword());
		cud.setToken(userVO.getToken());
//		List<>
		
		//역할
//		cud.setAuthorities(Arrays.asList(
//	            new SimpleGrantedAuthority("ROLE_USER"),
//	            new SimpleGrantedAuthority("ROLE_ADMIN")
//	        ));
		if ("ADMIN".equals(userVO.getRole())) {
			cud.setAuthorities(Collections.singletonList
					(new SimpleGrantedAuthority("ROLE_ADMIN")));
		} else {
			cud.setAuthorities(Collections.singletonList
					(new SimpleGrantedAuthority("ROLE_USER")));
		}
		
		cud.setEnabled(true);
		cud.setAccountNonExpired(true);
		cud.setAccountNonLocked(true);
		cud.setCredentialsNonExpired(true);
		System.out.println(">>>>>cud : " + cud);
		return cud;
	}

}
