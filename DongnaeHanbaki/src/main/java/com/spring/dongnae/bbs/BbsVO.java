package com.spring.dongnae.bbs;

public class BbsVO {
	String bbsIdx;
	String markerIdx;
	String writer;
	String mapIdx;
	String title;
	String content;
	String writeDate;
	String image;
	String hit;
	String latitude;
	String logitude;
	String delYn;
	public String getBbsIdx() {
		return bbsIdx;
	}
	public void setBbsIdx(String bbsIdx) {
		this.bbsIdx = bbsIdx;
	}
	public String getMarkerIdx() {
		return markerIdx;
	}
	public void setMarkerIdx(String markerIdx) {
		this.markerIdx = markerIdx;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getMapIdx() {
		return mapIdx;
	}
	public void setMapIdx(String mapIdx) {
		this.mapIdx = mapIdx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLogitude() {
		return logitude;
	}
	public void setLogitude(String logitude) {
		this.logitude = logitude;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "BbsVO [bbsIdx=" + bbsIdx + ", markerIdx=" + markerIdx + ", writer=" + writer + ", mapIdx=" + mapIdx
				+ ", title=" + title + ", content=" + content + ", writeDate=" + writeDate + ", image=" + image
				+ ", imagePi=" + ", hit=" + hit + ", latitude=" + latitude + ", logitude=" + logitude
				+ ", delYn=" + delYn + "]";
	}
	
}
