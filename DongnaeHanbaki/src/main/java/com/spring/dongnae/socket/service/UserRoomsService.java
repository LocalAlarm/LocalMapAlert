package com.spring.dongnae.socket.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.UserRooms;

@Service
public class UserRoomsService {

    @Autowired
    private UserRoomsRepository userRoomsRepository;
    
    public UserRooms getUserRoomsById(String id) {
        Optional<UserRooms> userRooms = userRoomsRepository.findByIdWithSelectedFields(id);
        return userRooms.orElse(null);
    }
}