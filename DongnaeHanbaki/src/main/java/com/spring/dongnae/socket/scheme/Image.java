package com.spring.dongnae.socket.scheme;

public class Image {
	private String image;
	private String imagePi;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImagePi() {
		return imagePi;
	}
	public void setImagePi(String imagePi) {
		this.imagePi = imagePi;
	}
	@Override
	public String toString() {
		return "Image [image=" + image + ", imagePi=" + imagePi + "]";
	}
}
