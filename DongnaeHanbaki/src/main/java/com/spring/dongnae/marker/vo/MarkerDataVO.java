package com.spring.dongnae.marker.vo;

public class MarkerDataVO {
	String markerdateIdx;
	String markerIdx;
	String title;
	String content;
	String writeDate;
	String image;
	String hit;
	double latitude;
	double longitude;
	public String getMarkerdateIdx() {
		return markerdateIdx;
	}
	public void setMarkerdateIdx(String markerdateIdx) {
		this.markerdateIdx = markerdateIdx;
	}
	public String getMarkerIdx() {
		return markerIdx;
	}
	public void setMarkerIdx(String markerIdx) {
		this.markerIdx = markerIdx;
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
	@Override
	public String toString() {
		return "MarkerDataVO [markerdateIdx=" + markerdateIdx + ", markerIdx=" + markerIdx + ", title=" + title
				+ ", content=" + content + ", writeDate=" + writeDate + ", image=" + image + ", hit=" + hit
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
