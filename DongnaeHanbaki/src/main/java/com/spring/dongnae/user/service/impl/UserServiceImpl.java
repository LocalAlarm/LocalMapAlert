package com.spring.dongnae.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dongnae.user.dao.UserDAO;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;


@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl() {
		System.out.println(">> UserServiceImpl() 객체생성");
	}

	@Override
	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}
	
	@Override
	public UserVO getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}
	
	@Override
	public int insertUser(UserVO vo) {
		return userDAO.insertUser(vo);
	}
	
	@Override
	public void insertKakaoUser(UserVO vo) {
		userDAO.insertKakaoUser(vo);
	}
	
	@Override
	public UserVO getIdUser(String emial) {
		return userDAO.getIdUser(emial);
	}
	
	// 이메일 중복체크 - 건희
	@Override	
	public int doubleCheckEmail(String email) {
		return userDAO.doubleCheckEmail(email);
	}
	
	// 이메일 찾기 - 건희
	@Override
	public String findUserEmail(UserVO vo) {
		return userDAO.findUserEmail(vo); 
	}

	@Override
	public UserVO getUserByToken(String token) {
		return userDAO.getUserByToken(token);
	}

	@Override
	public UserVO searchUserByEmail(String email) {
		return userDAO.searchUserByEmail(email);
	}
	//비번찾기 중 이메일 찾기
	@Override
	public String findPasswordByEmail(String email) {
		return userDAO.findPasswordByEmail(email);
	}

	//비번바꾸기
	@Override
	public void updatePassowrd(UserVO vo) {
		userDAO.updatePassowrd(vo);
	}

	//프로필 수정
	@Override
	public void updateProfile(Map<String, Object> map) {
		userDAO.updateProfile(map);
	}
	
	@Override
	public List<UserVO> searchFriendByEmail(String email) {
		return userDAO.searchFriendByEmail(email);
	}
	
	//kakao
//    @Override
//    public String getAccessToken(String authorize_code) {
//    	String access_Token = "";
//        String refresh_Token = "";
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
//        try {
//            URL url = new URL(reqURL);
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로 변경을 해주세요
//
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//            // BufferedWriter 간단하게 파일을 끊어서 보내기로 토큰값을 받아오기위해 전송
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            StringBuilder sb = new StringBuilder();
//            sb.append("grant_type=authorization_code");
//            sb.append("&client_id=");  //발급받은 key
//            sb.append("&redirect_uri=");     // 본인이 설정해 놓은 redirect_uri 주소
//            sb.append("&code=" + authorize_code);
//            bw.write(sb.toString());
//            bw.flush();
//
//            //    결과 코드가 200이라면 성공
//            // 여기서 안되는경우가 많이 있어서 필수 확인 !! **
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode + "확인");
//
//            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            System.out.println("response body : " + result + "결과");
//
//            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//
//            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//            System.out.println("access_token : " + access_Token);
//            System.out.println("refresh_token : " + refresh_Token);
//
//            br.close();
//            bw.close();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//        return access_Token;
//    }


//    @Override
//    public String getuserinfo(String access_Token, HttpSession session, RedirectAttributes rttr) {
//    	 HashMap<String, Object> userInfo = new HashMap<>();
//    	    log.info("getuserinfo()");
//
//    	    String requestURL = "https://kapi.kakao.com/v2/user/me";
//    	    String view = null;
//    	    String msg = null;
//
//    	    try {
//    	        URL url = new URL(requestURL); //1.url 객체만들기
//    	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//    	        //2.url 에서 url connection 만들기
//    	        conn.setRequestMethod("GET"); // 3.URL 연결구성
//    	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//
//    	        //키 값, 속성 적용
//    	        int responseCode = conn.getResponseCode(); //서버에서 보낸 http 상태코드 반환
//    	        System.out.println("responseCode :" + responseCode + "여긴가");
//    	        BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//    	        // 버퍼를 사용하여 읽은 것
//    	        String line = "";
//    	        String result = "";
//    	        while ((line = buffer.readLine()) != null) {
//    	            result += line;
//    	        }
//    	        //readLine()) ==> 입력 String 값으로 리턴값 고정
//
//    	        System.out.println("response body :" + result);
//
//    	        // 읽었으니깐 데이터꺼내오기
//    	        JsonParser parser = new JsonParser();
//    	        JsonElement element = parser.parse(result); //Json element 문자열변경
//    	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
//    	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//
//    	        String mnickname = properties.getAsJsonObject().get("nickname").getAsString();
//    	        String mmail = kakao_account.getAsJsonObject().get("email").getAsString();
//    	        
//    			//userInfo에 사용자 정보 저장
//    	        userInfo.put("mid", mmail);
//    	        userInfo.put("mnickname", mnickname);
//
//    	        log.info(String.valueOf(userInfo));
//
//    	    } catch (Exception e) {
//    	        e.printStackTrace();
//    	    }
//
//    	    MemberDto member = memberDao.findkakao(userInfo);
//    	    // 저장되어있는지 확인
//    	    log.info("S :" + member);
//
//    	    if (member == null) {
//    	        //member null 이면 정보가 저장 안되어있는거라서 정보를 저장.
//    	        memberDao.kakaoinsert(userInfo);
//    	        //저장한 member 정보 다시 가져오기 HashMap이라 형변환 시켜줌
//    	        member = loginnDao.selectMember((String)userInfo.get("mid"));
//    	        session.setAttribute("member", member);
//    			
//    	        //로그인 처리 후 메인 페이지로 이동
//    	        view = "redirect:/";
//    	        msg = "로그인 성공";
//    	    } else {
//    	        session.setAttribute("member", member);
//    	        view = "redirect:/";
//    	        msg = "로그인 성공";
//
//    	    }
//    	    rttr.addFlashAttribute("msg", msg);
//    	    return view;
//    	}
//    }
}

	







