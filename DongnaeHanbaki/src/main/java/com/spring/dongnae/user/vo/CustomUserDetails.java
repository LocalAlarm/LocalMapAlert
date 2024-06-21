package com.spring.dongnae.user.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends UserVO implements UserDetails {
   private static final long serialVersionUID = 1L; // serialVersionUID 필드 추가

   private String username;
    private String password;
    private String token;
    private String image;
    private String nickname;
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
    }
    
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
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

   @Override
   public String toString() {
      return "CustomUserDetails [username=" + username + ", password=" + password + ", token=" + token
            + ", isEnabled=" + isEnabled + ", isAccountNonExpired=" + isAccountNonExpired + ", isAccountNonLocked="
            + isAccountNonLocked + ", isCredentialsNonExpired=" + isCredentialsNonExpired + ", authorities="
            + authorities + "]";
   }

}
