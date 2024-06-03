package com.spring.dongnae.user.dao;

import com.spring.dongnae.user.vo.UserVO;

public interface UserDAO {
	UserVO getUser(UserVO vo);
	void insertKakaoUser(UserVO vo);
	void insertUser(UserVO vo);
<<<<<<< HEAD
	UserVO getIdUser(String email);
	
=======
	String doubleCheckEmail(String email);
>>>>>>> branch 'gun1' of https://github.com/LocalAlarm/LocalMapAlert.git
}
