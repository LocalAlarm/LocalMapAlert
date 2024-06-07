package com.spring.dongnae.bbs;

public class MakerVO {
	String maker_idx;
	String type;
	String image;
	String image_pi;
	public String getMaker_idx() {
		return maker_idx;
	}
	public void setMaker_idx(String maker_idx) {
		this.maker_idx = maker_idx;
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
	public String getImage_pi() {
		return image_pi;
	}
	public void setImage_pi(String image_pi) {
		this.image_pi = image_pi;
	}
	@Override
	public String toString() {
		return "MakerVO [maker_idx=" + maker_idx + ", type=" + type + ", image=" + image + ", image_pi=" + image_pi
				+ "]";
	}
	
}
