package com.spring.dongnae.socket.dto;

public class MoimDto {
    private String id;
    private String chatId;
    private String name;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	private String profilePic;
	@Override
	public String toString() {
		return "MoimDto [id=" + id + ", chatId=" + chatId + ", name=" + name + ", profilePic=" + profilePic + "]";
	}
}
