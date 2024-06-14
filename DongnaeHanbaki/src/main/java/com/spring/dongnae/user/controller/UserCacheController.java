package com.spring.dongnae.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.user.cache.CacheManagerWrapper;
import com.spring.dongnae.user.dto.UserCacheDTO;
import com.spring.dongnae.user.service.UserCacheService;

@RestController
@RequestMapping("/usersCache")
public class UserCacheController {

    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private CacheManagerWrapper cacheManagerWrapper;

    @PostMapping("/token")
    public UserCacheDTO getUserByToken(@RequestParam String token) {
        System.out.println("Controller - Token: " + token);
        UserCacheDTO dto = userCacheService.getUserByToken(token);
        if (dto == null) {
            // 캐시에 데이터가 없는 경우 데이터베이스에서 조회
            dto = userCacheService.getUserByTokenFromDatabase(token);
            if (dto != null) {
                // 데이터베이스에서 조회한 데이터를 캐시에 저장
                userCacheService.putUserInCache(dto);
                System.out.println("캐쉬!!" + dto.toString());
            }
        }
        System.out.println("Controller - DTO: " + (dto != null ? dto.toString() : "null"));
        return dto;
    }

    @PostMapping("/email")
    public UserCacheDTO getUserByEmail(@RequestParam String email) {
        System.out.println("Controller - Email: " + email);
        UserCacheDTO dto = userCacheService.getUserByEmail(email);
        if (dto == null) {
            // 캐시에 데이터가 없는 경우 데이터베이스에서 조회
            dto = userCacheService.getUserByEmailFromDatabase(email);
            if (dto != null) {
                // 데이터베이스에서 조회한 데이터를 캐시에 저장
                userCacheService.putUserInCache(dto);
                System.out.println("캐쉬!!" + dto.toString());
            }
        }
        System.out.println("Controller - DTO: " + (dto != null ? dto.toString() : "null"));
        return dto;
    }
    
    @PostMapping("/search")
    public List<UserCacheDTO> searchUsersByEmail(@RequestParam String email) {
        return userCacheService.searchUsersByEmail(email);
    }
    
    @PostMapping("/cache")
    public Map<Object, Object> getAllCacheEntries() {
    	cacheManagerWrapper.printAllCacheKeys();
        return userCacheService.getAllCacheEntries();
    }
}
