package com.spring.dongnae.bbs;

public class MarkerVO {
	String markerIdx;
	String userEmail;
	String limitRole;
	String type;
	String content;
	String imageIdx;
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
	public String getImageIdx() {
		return imageIdx;
	}
	public void setImageIdx(String imageIdx) {
		this.imageIdx = imageIdx;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	
	public String getLimitRole() {
		return limitRole;
	}
	public void setLimitRole(String limitRole) {
		this.limitRole = limitRole;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MarkerVO [markerIdx=" + markerIdx + ", userEmail=" + userEmail + ", limitRole=" + limitRole + ", type="
				+ type + ", content=" + content + ", imageIdx=" + imageIdx + ", delYn=" + delYn + "]";
	}
}
