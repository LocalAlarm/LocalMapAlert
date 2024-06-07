package com.spring.dongnae.bbs;

public class BbsVO {
	String bbs_idx;
	String maker_idx;
	String writer;
	String title;
	String content;
	String write_date;
	String hit;
	String latitude;
	String longitude;
	public String getBbs_idx() {
		return bbs_idx;
	}
	public void setBbs_idx(String bbs_idx) {
		this.bbs_idx = bbs_idx;
	}
	public String getMaker_idx() {
		return maker_idx;
	}
	public void setMaker_idx(String maker_idx) {
		this.maker_idx = maker_idx;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
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
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
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
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "BbsVO [bbs_idx=" + bbs_idx + ", maker_idx=" + maker_idx + ", writer=" + writer + ", title=" + title
				+ ", content=" + content + ", write_date=" + write_date + ", hit=" + hit + ", latitude=" + latitude
				+ ", logitude=" + longitude + "]";
	}
}
