package com.spring.dongnae.user.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
   private static final long serialVersionUID = 1L; // serialVersionUID 필드 추가

    private String username;
    private String password;
    private String token;
    private String image;
    private String nickname;
	private String address;
	private String detailAddress;
	private String email;
	private String recoverEmail;
	private String imagePi;
	private int kakaoCheck;
	private String role;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private Collection<? extends GrantedAuthority> authorities ;
   
    public CustomUserDetails() {
    }
    
    public CustomUserDetails(UserVO userVO) {
       this.setUsername(userVO.getEmail());
       this.setPassword(userVO.getPassword());
       this.setToken(userVO.getToken());
       this.setImage(userVO.getImage());
       this.setNickname(userVO.getNickname());
       this.setAddress(userVO.getAddress());
       this.setDetailAddress(userVO.getDetailAddress());
       this.setEmail(userVO.getEmail());
       this.setRecoverEmail(userVO.getRecoverEmail());
       this.setImagePi(userVO.getImagePi());
       this.setKakaoCheck(userVO.getKakaoCheck());
       this.setRole(userVO.getRole());
    }
    
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }
   
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDetailAddress() {
		return detailAddress;
	}
	
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	public String getRecoverEmail() {
		return recoverEmail;
	}
	
	public void setRecoverEmail(String recoverEmail) {
		this.recoverEmail = recoverEmail;
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
	      this.authorities = authorities;
   }

   @Override
   public String getPassword() {
      return password;
   }
   
   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public String getUsername() {
      return username;
   }
   
   public void setUsername(String username) {
      this.username = username;
   }
   
   public String getToken() {
      return token;
   }
   
   public void setToken(String token) {
      this.token = token;
   }

   @Override
   public boolean isAccountNonExpired() {
      return isAccountNonExpired;
   }
   
   public void setAccountNonExpired(boolean isAccountNonExpired) {
      this.isAccountNonExpired = isAccountNonExpired;
   }

   @Override
   public boolean isAccountNonLocked() {
      return isAccountNonLocked;
   }
   
   public void setAccountNonLocked(boolean isAccountNonLocked) {
      this.isAccountNonLocked = isAccountNonLocked;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return isCredentialsNonExpired;
   }
   
   public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
      this.isCredentialsNonExpired = isCredentialsNonExpired;
   }

   @Override
   public boolean isEnabled() {
      return isEnabled;
   }
   
   public void setEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
   }
   
   public String getImage() {
      return image;
   }
   
   public void setImage(String image) {
      this.image = image;
   }
   public String getNickname() {
      return nickname;
   }
   
   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   public void setCustomUserDetails(CustomUserDetails cud) {
	    this.username = cud.getUsername();
	    this.password = cud.getPassword();
	    this.token = cud.getToken();
	    this.image = cud.getImage();
	    this.nickname = cud.getNickname();
	    this.address = cud.getAddress();
	    this.detailAddress = cud.getDetailAddress();
	    this.email = cud.getEmail();
	    this.recoverEmail = cud.getRecoverEmail();
	    this.imagePi = cud.getImagePi();
	    this.kakaoCheck = cud.getKakaoCheck();
	    this.role = cud.getRole();
	}
   
   public void setCustomUserDetails(String username, String password, String token, String image, String nickname,
           String address, String detailAddress, String email, String recoverEmail, String imagePi, int kakaoCheck,
           String role) {
       this.username = username;
       this.password = password;
       this.token = token;
       this.image = image;
       this.nickname = nickname;
       this.address = address;
       this.detailAddress = detailAddress;
       this.email = email;
       this.recoverEmail = recoverEmail;
       this.imagePi = imagePi;
       this.kakaoCheck = kakaoCheck;
       this.role = role;
   }

	@Override
	public String toString() {
		return "CustomUserDetails [username=" + username + ", password=" + password + ", token=" + token + ", image="
				+ image + ", nickname=" + nickname + ", address=" + address + ", detailAddress=" + detailAddress
				+ ", email=" + email + ", recoverEmail=" + recoverEmail + ", imagePi=" + imagePi + ", kakaoCheck="
				+ kakaoCheck + ", role=" + role + ", isEnabled=" + isEnabled + ", isAccountNonExpired="
				+ isAccountNonExpired + ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired="
				+ isCredentialsNonExpired + ", authorities=" + authorities + "]";
	}
   
}
