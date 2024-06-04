package com.spring.dongnae.bbs;

public class MapVO {
	String mapIdx;
	String userEmail;
	String limitRole;
	String title;
	String centerIatitude;
	String centerIogitude;
	String delYn;
	public String getMapIdx() {
		return mapIdx;
	}
	public void setMapIdx(String mapIdx) {
		this.mapIdx = mapIdx;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getLimitRole() {
		return limitRole;
	}
	public void setLimitRole(String limitRole) {
		this.limitRole = limitRole;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCenterIatitude() {
		return centerIatitude;
	}
	public void setCenterIatitude(String centerIatitude) {
		this.centerIatitude = centerIatitude;
	}
	public String getCenterIogitude() {
		return centerIogitude;
	}
	public void setCenterIogitude(String centerIogitude) {
		this.centerIogitude = centerIogitude;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "MapVO [mapIdx=" + mapIdx + ", userEmail=" + userEmail + ", limitRole=" + limitRole + ", title=" + title
				+ ", centerIatitude=" + centerIatitude + ", centerIogitude=" + centerIogitude + ", delYn=" + delYn
				+ "]";
	}
	
}
