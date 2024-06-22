package com.spring.dongnae.socket.dto;

import com.spring.dongnae.socket.scheme.Message;
import com.spring.dongnae.user.vo.CustomUserDetails;
import com.spring.dongnae.user.vo.UserVO;

public class MessageDto {
	private static final long serialVersionUID = 1L;
	private String roomId;
	private String senderEmail;
	private String senderNickName;
	private String senderProfileImage;
	private String content;
	
	public MessageDto(CustomUserDetails cud, Message message) {
		this.setSenderEmail(cud.getUsername());
		this.setSenderNickName(cud.getNickname());
		this.setSenderProfileImage(cud.getImage());
		this.setRoomId(message.getRoomId());
		this.setContent(message.getContent());
	}
	
	public MessageDto(UserVO userVO, Message message) {
		this.setSenderEmail(userVO.getEmail());
		this.setSenderNickName(userVO.getNickname());
		this.setSenderProfileImage(userVO.getImage());
		this.setRoomId(message.getRoomId());
		this.setContent(message.getContent());
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderNickName() {
		return senderNickName;
	}

	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}

	public String getSenderProfileImage() {
		return senderProfileImage;
	}

	public void setSenderProfileImage(String senderProfileImage) {
		this.senderProfileImage = senderProfileImage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MessageDto [roomId=" + roomId + ", senderEmail=" + senderEmail + ", senderNickName=" + senderNickName
				+ ", senderProfileImage=" + senderProfileImage + ", content=" + content + "]";
	}
	
}