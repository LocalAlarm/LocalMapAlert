package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.dto.MoimDto;
import com.spring.dongnae.socket.dto.UserRoomsDto;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.UserRooms;

@Service
public class UserRoomsService {

    @Autowired
    private UserRoomsRepository userRoomsRepository;
    
    public UserRooms getUserRoomsById(String id) {
        Optional<UserRooms> userRooms = userRoomsRepository.findById(id);
        return userRooms.orElse(null);
    }
    
    public UserRoomsDto getUserRoomsDtoById(String id) {
        UserRooms userRooms = userRoomsRepository.findById(id).orElseThrow(() -> new RuntimeException("UserRooms not found"));

        List<MoimDto> moims = userRooms.getMoims().stream().map(moim -> {
            MoimDto moimDto = new MoimDto();
            moimDto.setId(moim.getId());
            moimDto.setChatId(moim.getChatRoomId());
            moimDto.setName(moim.getName());
            moimDto.setProfilePic(moim.getProfilePic());
            return moimDto;
        }).collect(Collectors.toList());

        List<MoimDto> masterMoims = userRooms.getMasterMoims().stream().map(moim -> {
            MoimDto moimDto = new MoimDto();
            moimDto.setId(moim.getId());
            moimDto.setChatId(moim.getChatRoomId());
            moimDto.setName(moim.getName());
            moimDto.setProfilePic(moim.getProfilePic());
            return moimDto;
        }).collect(Collectors.toList());

        UserRoomsDto userRoomsDto = new UserRoomsDto();
        userRoomsDto.setId(userRooms.getId());
        userRoomsDto.setEmail(userRooms.getEmail());
        userRoomsDto.setMoims(moims);
        userRoomsDto.setMasterMoims(masterMoims);
        userRoomsDto.setRequestIds(userRooms.getRequestIds());
        userRoomsDto.setFriendIds(userRooms.getFriendIds());

        return userRoomsDto;
    }
}