package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.dto.MoimDto;
import com.spring.dongnae.socket.dto.UserRoomsDto;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.FriendInfo;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Service
public class UserRoomsService {

    @Autowired
    private UserRoomsRepository userRoomsRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GetAuthenticInfo getAuthenticInfo;
    
    public UserRooms getUserRoomsById(String id) {
        Optional<UserRooms> userRooms = userRoomsRepository.findById(id);
        return userRooms.orElse(null);
    }
    
    public UserRooms addFriendRequest(String email, String token) throws Exception {
    	UserRooms userRooms = userRoomsRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("UserRooms not found"));
    	userRooms.addFriendRequest(token);
    	userRoomsRepository.save(userRooms);
    	return userRooms;
    }
    
    public List<UserVO> getRequestIds() throws Exception {
    	UserRooms userRooms = userRoomsRepository.findById(getAuthenticInfo.GetToken()).orElseThrow(() -> new RuntimeException("UserRooms not found"));
    	List<String> requestTokens = userRooms.getRequestIds();
    	return requestTokens.stream().map(userService::getUserByToken).collect(Collectors.toList());
    }
    
    public List<UserVO> getFriendIds() throws Exception {
    	UserRooms userRooms = userRoomsRepository.findById(getAuthenticInfo.GetToken()).orElseThrow(() -> new RuntimeException("UserRooms not found"));
    	List<FriendInfo> friendInfo = userRooms.getFriendIds();
    	List<UserVO> friendUserVOLists = friendInfo.stream().map(friend -> userService.getUserByToken(friend.getFriendToken())).collect(Collectors.toList());
    	return friendUserVOLists;
    }
    
    public boolean processApproveFriendRequest(String requestedEmail) throws Exception {
    	try {
        	String myEmail = getAuthenticInfo.GetEmail();
        	UserRooms userRooms = userRoomsRepository.findByEmail(myEmail).orElseThrow(() -> new RuntimeException("UserRooms not found"));
        	UserRooms requestedUserRooms = userRoomsRepository.findByEmail(requestedEmail).orElseThrow(() -> new RuntimeException("UserRooms not found"));
        	ChatRoom chatRoom = new ChatRoom();
        	chatRoom.addUser(requestedUserRooms);
        	chatRoom.addUser(userRooms);
        	chatRoomRepository.save(chatRoom);
        	
        	FriendInfo myFriendInfo = new FriendInfo(chatRoom.getId(), requestedUserRooms.getId(), requestedUserRooms.getEmail());
        	FriendInfo requestedFriendInfo = new FriendInfo(chatRoom.getId(), userRooms.getId(), userRooms.getEmail());
        	
        	userRooms.addFriendId(myFriendInfo);
        	requestedUserRooms.addFriendId(requestedFriendInfo);
        	
        	userRoomsRepository.save(userRooms);
        	userRoomsRepository.save(requestedUserRooms);
        	return true;
    	} catch (Exception e) {
    		return false;
    	} 	
    }
    
    public boolean processRejectFriendReqeust(String rejcetEmail) throws Exception {
    	try {
        	String myEmail = getAuthenticInfo.GetEmail();
        	UserRooms userRooms = userRoomsRepository.findByEmail(myEmail).orElseThrow(() -> new RuntimeException("UserRooms not found"));
        	UserRooms rejectRooms = userRoomsRepository.findByEmail(rejcetEmail).orElseThrow(() -> new RuntimeException("UserRooms not found"));
        	userRooms.removeFriendRequest(rejectRooms.getId());
        	userRoomsRepository.save(userRooms);
        	return true;
    	} catch (Exception e) {
    		return false;
    	}
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