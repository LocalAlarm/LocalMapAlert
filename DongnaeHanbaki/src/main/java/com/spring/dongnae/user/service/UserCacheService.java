package com.spring.dongnae.user.service;

import java.util.List;
import java.util.Map;

import com.spring.dongnae.user.dto.UserCacheDTO;

public interface UserCacheService {
    UserCacheDTO getUserByToken(String token);
    UserCacheDTO getUserByEmail(String email);
    List<UserCacheDTO> searchUsersByEmail(String email);
    void refreshUserCache();
    Map<Object, Object> getAllCacheEntries();
    void putUserInCache(UserCacheDTO user);
    UserCacheDTO getUserByTokenFromDatabase(String token);
    UserCacheDTO getUserByEmailFromDatabase(String email);
}