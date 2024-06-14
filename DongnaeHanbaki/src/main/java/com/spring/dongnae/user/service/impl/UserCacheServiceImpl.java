package com.spring.dongnae.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.spring.dongnae.user.cache.CacheManagerWrapper;
import com.spring.dongnae.user.dao.UserCacheDAO;
import com.spring.dongnae.user.dto.UserCacheDTO;
import com.spring.dongnae.user.service.UserCacheService;

@Service(value = "userCacheService")
public class UserCacheServiceImpl implements UserCacheService {
    @Autowired
    private UserCacheDAO userCacheDAO;

    @Autowired
    private CacheManagerWrapper cacheManagerWrapper;

    @Override
    public UserCacheDTO getUserByToken(String token) {
        // 캐시에서 데이터를 조회합니다.
        UserCacheDTO user = cacheManagerWrapper.getCacheEntry("users", token, UserCacheDTO.class);
        if (user == null) {
            // 캐시에 데이터가 없으면 데이터베이스에서 조회
            user = userCacheDAO.getUserByTokenFromDatabase(token);
            if (user != null) {
                // 캐시에 저장
                putUserInCache(user);
            }
        } else {
        	System.out.println("캐쉬에 존재!!" + user.toString());
        }
        return user;
    }

    @Override
    public UserCacheDTO getUserByEmail(String email) {
        // 캐시에서 데이터를 조회합니다.
        UserCacheDTO user = cacheManagerWrapper.getCacheEntry("users", email, UserCacheDTO.class);
        if (user == null) {
            // 캐시에 데이터가 없으면 데이터베이스에서 조회
            user = userCacheDAO.getUserByEmailFromDatabase(email);
            if (user != null) {
                // 캐시에 저장
                putUserInCache(user);
            }
        } else {
        	System.out.println("캐쉬에 존재!!" + user.toString());
        }
        return user;
    }

    @Override
    public List<UserCacheDTO> searchUsersByEmail(String email) {
        List<UserCacheDTO> users = userCacheDAO.searchUsersByEmail(email);
        System.out.println("Service - searchUserByEmail: " + users.size() + " users found");
        return users;
    }

    @Override
    public void refreshUserCache() {
        cacheManagerWrapper.refreshCacheEntries();
    }
    
    @Override
    public Map<Object, Object> getAllCacheEntries() {
        return cacheManagerWrapper.getAllCacheEntries();
    }

    @Override
    public void putUserInCache(UserCacheDTO user) {
        cacheManagerWrapper.putCacheEntry("users", user.getToken(), user);
        cacheManagerWrapper.putCacheEntry("users", user.getEmail(), user);
        System.out.println("Service - putUserInCache: " + user.toString());
    }

    @Override
    public UserCacheDTO getUserByTokenFromDatabase(String token) {
        return userCacheDAO.getUserByTokenFromDatabase(token);
    }

    @Override
    public UserCacheDTO getUserByEmailFromDatabase(String email) {
        return userCacheDAO.getUserByEmailFromDatabase(email);
    }

}