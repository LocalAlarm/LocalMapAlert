package com.spring.dongnae.user.dao;

import java.util.List;

import com.spring.dongnae.user.dto.UserCacheDTO;

public interface UserCacheDAO {
    UserCacheDTO getUserByTokenFromDatabase(String token);
    UserCacheDTO getUserByEmailFromDatabase(String email);
    List<UserCacheDTO> searchUsersByEmail(String email);
    List<UserCacheDTO> findAllUsers();
}