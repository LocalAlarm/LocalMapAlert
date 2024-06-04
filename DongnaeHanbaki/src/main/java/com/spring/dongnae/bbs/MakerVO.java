package com.spring.dongnae.bbs;

public class MakerVO {
	String makerIdx;
	String userEmail;
	String limitRole;
	String type;
	String image;
	String imagePi;
	String delYn;
	public String getMakerIdx() {
		return makerIdx;
	}
	public void setMakerIdx(String makerIdx) {
		this.makerIdx = makerIdx;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getRole() {
		return limitRole;
	}
	public void setRole(String limitRole) {
		this.limitRole = limitRole;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImagePi() {
		return imagePi;
	}
	public void setImagePi(String imagePi) {
		this.imagePi = imagePi;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "MakerVO [makerIdx=" + makerIdx + ", userEmail=" + userEmail + ", limitRole=" + limitRole + ", type=" + type
				+ ", image=" + image + ", imagePi=" + imagePi + ", delYn=" + delYn + "]";
	}
	
}
