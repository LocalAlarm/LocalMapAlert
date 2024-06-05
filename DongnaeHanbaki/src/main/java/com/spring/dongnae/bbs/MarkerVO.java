package com.spring.dongnae.bbs;

public class MarkerVO {
	String markerIdx;
	String userEmail;
	String limitRole;
	String type;
	String image;
	String delYn;
	public String getMarkerIdx() {
		return markerIdx;
	}
	public void setMarkerIdx(String markerIdx) {
		this.markerIdx = markerIdx;
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
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "MakerVO [markerIdx=" + markerIdx + ", userEmail=" + userEmail + ", limitRole=" + limitRole + ", type=" + type
				+ ", image=" + image + ", imagePi=" + ", delYn=" + delYn + "]";
	}
	
}
