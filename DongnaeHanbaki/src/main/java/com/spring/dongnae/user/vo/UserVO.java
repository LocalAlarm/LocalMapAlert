package com.spring.dongnae.user.vo;



public class UserVO {
	private static final long serialVersionUID = 1L; // serialVersionUID 필드 추가

	private String email;
	private String password;
	private String nickname;
	private String address;
	private String detailAddress;
	private String recoverEmail;
	private String image;
	private String imagePi;
	private int kakaoCheck;
	private String token;
	private String role;

//	private Collection<? extends GrantedAuthority> authorities;

	// 상세주소, 복구이메일 추가 - 건희
	public UserVO() {
		System.out.println(">> UserVO() 객체생성");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

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

	public int getKakaoCheck() {
		return kakaoCheck;
	}

	public void setKakaoCheck(int kakaoCheck) {
		this.kakaoCheck = kakaoCheck;
	}

	public String getRecoverEmail() {
		return recoverEmail;
	}

	public void setRecoverEmail(String recoverEmail) {
		this.recoverEmail = recoverEmail;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserVO [email=" + email + ", password=" + password + ", nickname=" + nickname + ", address=" + address
				+ ", detailAddress=" + detailAddress + ", recoverEmail=" + recoverEmail + ", image=" + image
				+ ", imagePi=" + imagePi + ", kakaoCheck=" + kakaoCheck + ", token=" + token + ", role=" + role + "]";
	}

}