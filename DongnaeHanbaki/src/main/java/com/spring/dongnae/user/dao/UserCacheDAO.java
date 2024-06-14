package com.spring.dongnae.user.dao;

import java.util.List;

import com.spring.dongnae.user.dto.UserCacheDTO;

public interface UserCacheDAO {
    UserCacheDTO getUserByToken(String token);
    UserCacheDTO getUserByEmail(String email);
    List<UserCacheDTO> searchUsersByEmail(String email);
    List<UserCacheDTO> findAllUsers();
}