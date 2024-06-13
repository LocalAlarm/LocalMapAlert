package com.spring.dongnae.bbs;

public class BbsVO {
	String bbsIdx;
	String markerIdx;
	String writer;
	String mapIdx;
	String title;
	String content;
	String writeDate;
	String imageIdx;
	String hit;
	double latitude;
	double longitude;
	String delYn;
	private int SEQ_BBS_IDX;

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
	public String getImageIdx() {
		return imageIdx;
	}
	public void setImageIdx(String imageIdx) {
		this.imageIdx = imageIdx;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
	    return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public int getSEQ_BBS_IDX() {
	    return SEQ_BBS_IDX;
	}
	public void setSEQ_BBS_IDX(int SEQ_BBS_IDX) {
	    this.SEQ_BBS_IDX = SEQ_BBS_IDX;
	}
	@Override
	public String toString() {
		return "BbsVO [bbsIdx=" + bbsIdx + ", markerIdx=" + markerIdx + ", writer=" + writer + ", mapIdx=" + mapIdx
				+ ", title=" + title + ", content=" + content + ", writeDate=" + writeDate + ", imageIdx=" + imageIdx
				+ ", imagePi=" + ", hit=" + hit + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", delYn=" + delYn + "]";
	}
	
}
