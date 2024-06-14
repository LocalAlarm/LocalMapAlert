package com.spring.dongnae.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.dongnae.user.dao.UserCacheDAO;
import com.spring.dongnae.user.dto.UserCacheDTO;
import com.spring.dongnae.user.service.UserCacheService;

@Service(value = "userCacheService")
public class UserCacheServiceImpl implements UserCacheService {
	@Autowired
	private UserCacheDAO userCacheDAO;
	
	@Autowired
    private CacheManager cacheManager;
	
	@Override
	@Cacheable(value = "users", key = "#token")
	public UserCacheDTO getUserByToken(String token) {
		return userCacheDAO.getUserByToken(token);
	}

	@Override
	@Cacheable(value = "users", key = "#email")
	public UserCacheDTO getUserByEmail(String email) {
		return userCacheDAO.getUserByEmail(email);
	}

	@Override
	public List<UserCacheDTO> searchUsersByEmail(String email) {
		return userCacheDAO.searchUsersByEmail(email);
	}

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void refreshUserCache() {
        cacheManager.getCache("users").clear();
        List<UserCacheDTO> users = userCacheDAO.findAllUsers();
        users.forEach(user -> {
            cacheManager.getCache("users").put(user.getToken(), user);
        });
    }

}
