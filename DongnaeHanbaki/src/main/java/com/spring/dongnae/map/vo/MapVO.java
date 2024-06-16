package com.spring.dongnae.map.vo;

public class MapVO {
	private int mapIdx;
	private String userEmail;
	private String title;
	private String content;
	private String viewLevel;
	private Double centerLatitude;
	private Double centerLongitude;
	private String createDate;
	private String openYn;
	private String delYn;
	
	public int getMapIdx() {
		return mapIdx;
	}
	public void setMapIdx(int mapIdx) {
		this.mapIdx = mapIdx;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getCenterIatitude() {
		return centerLatitude;
	}
	public void setCenterIatitude(Double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}
	public Double getCenterIogitude() {
		return centerLongitude;
	}
	public void setCenterIogitude(Double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getViewLevel() {
		return viewLevel;
	}
	public void setViewLevel(String viewLevel) {
		this.viewLevel = viewLevel;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	@Override
	public String toString() {
		return "MapVO [mapIdx=" + mapIdx + ", userEmail=" + userEmail + ", title=" + title + ", content=" + content
				+ ", viewLevel=" + viewLevel + ", centerLatitude=" + centerLatitude + ", centerLongitude="
				+ centerLongitude + ", createDate=" + createDate + ", openYn=" + openYn + ", delYn=" + delYn + "]";
	}
	
	
	
}
