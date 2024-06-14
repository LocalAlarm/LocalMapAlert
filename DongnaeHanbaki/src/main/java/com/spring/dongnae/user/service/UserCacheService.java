package com.spring.dongnae.user.service;

import java.util.List;
import java.util.Map;

import com.spring.dongnae.user.dto.UserCacheDTO;

public interface UserCacheService {
    // 토큰 값으로 유저 정보 조회
    UserCacheDTO getUserByToken(String token);

    // 이메일 값으로 유저 정보 조회
    UserCacheDTO getUserByEmail(String email);

    // 이메일로 유저 리스트 조회
    List<UserCacheDTO> searchUsersByEmail(String email);

    // 캐시 갱신 메소드
    void refreshUserCache();
}