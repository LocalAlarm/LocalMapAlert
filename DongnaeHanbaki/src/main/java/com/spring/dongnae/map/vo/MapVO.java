package com.spring.dongnae.map.vo;

public class MapVO {
	private String mapIdx;
	private String userEmail;
	private String title;
	private String content;
	private String viewLevel;
	private Double centerIatitude;
	private Double centerIogitude;
	private String createDate;
	private String openYn;
	private String delYn;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getCenterIatitude() {
		return centerIatitude;
	}
	public void setCenterIatitude(Double centerIatitude) {
		this.centerIatitude = centerIatitude;
	}
	public Double getCenterIogitude() {
		return centerIogitude;
	}
	public void setCenterIogitude(Double centerIogitude) {
		this.centerIogitude = centerIogitude;
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
				+ ", viewLevel=" + viewLevel + ", centerIatitude=" + centerIatitude + ", centerIogitude="
				+ centerIogitude + ", createDate=" + createDate + ", openYn=" + openYn + ", delYn=" + delYn + "]";
	}
	
}
