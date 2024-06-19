//package com.spring.dongnae.socket.controller;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.spring.dongnae.socket.repo.ChatRoomRepository;
//import com.spring.dongnae.socket.repo.FriendRoomRepository;
//import com.spring.dongnae.socket.repo.UserRoomsRepository;
//import com.spring.dongnae.socket.scheme.ApproveFriendRequest;
//import com.spring.dongnae.socket.scheme.ChatRoom;
//import com.spring.dongnae.socket.scheme.FriendInfo;
//import com.spring.dongnae.socket.scheme.FriendRequest;
//import com.spring.dongnae.socket.scheme.FriendRoom;
//import com.spring.dongnae.user.service.UserService;
//import com.spring.dongnae.utils.auth.GetAuthenticInfo;
//
//@RestController
//public class FriendRequestController {
//
//    @Autowired
//    private GetAuthenticInfo getAuthenticInfo;
//    @Autowired
//	private FriendRoomRepository friendRoomRepository;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//    @Autowired
//    private UserRoomsRepository userRoomsRepository;
//    
//    private ApproveFriendRequest approveFriendRequest;
//    
//    @PostMapping("/api/sendFriendRequest") // POST 요청을 처리하는 메서드를 정의합니다.
//    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) { // 요청 본문에 있는 FriendRequest 객체를 인자로 받습니다. 
//    	try {
//            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(friendRequest.getRequestEmail()); // 친구 요청을 받는 사용자의 이메일로 FriendRoom 객체를 찾습니다.
//            FriendRoom friendRoom = optionalFriendRoom.get(); // Optional 객체에서 FriendRoom 객체를 가져옵니다. 
//            friendRoom.addFriendRequest(getAuthenticInfo.GetEmail()); // 로그인한 사용자의 이름을 친구 요청 목록에 추가합니다.
//            friendRoom.setRequestIds(friendRoom.getRequestIds().stream().distinct().collect(Collectors.toList())); // 친구 요청 목록에서 중복 항목을 제거합니다.
//            friendRoomRepository.save(friendRoom); // 변경된 FriendRoom 객체를 데이터베이스에 저장합니다.
//        } catch(Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 해주세요."); // 사용자가 로그인하지 않은 경우, 에러 메시지를 반환합니다.
//        }
//        return ResponseEntity.ok("친구 요청이 성공적으로 전송되었습니다!"); // 모든 작업이 성공적으로 완료되면, 성공 메시지를 반환합니다.
//    }
//
//    //친구요청 받아온걸 화면에 띄워주는 코드
//    @PostMapping("/api/receiveFriendRequest")
//    public ResponseEntity<List<String>> receiveFriendRequest() {
//        Optional<FriendRoom> optionalReceiveFriend = friendRoomRepository.findByEmail(getAuthenticInfo.GetEmail()); // 친구요청 받는 사용자 이메일로 찾기
//        if (optionalReceiveFriend.isPresent()) { // 있으면
//            FriendRoom friendRoom = optionalReceiveFriend.get();
//            // 필요한 로직 수행 후 친구 요청 목록 반환 
//            return ResponseEntity.ok(friendRoom.getRequestIds());
//        }
//        return ResponseEntity.notFound().build();
//    }
//    
//    @PostMapping(value = "/api/approveFriendRequest", produces = "text/plain;charset=UTF-8")
//    public ResponseEntity<String> approveFriendRequest(@RequestBody ApproveFriendRequest approveRequest) {
//        try {
//            String requestId = approveRequest.getRequestId();
//            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(requestId);
//            Optional<FriendRoom> optionalMyFriendRoom = friendRoomRepository.findByEmail(getAuthenticInfo.GetEmail());
//            ChatRoom chatRoom = new ChatRoom();
//            chatRoom.addUser(userRoomsRepository.findById(userService.getUserByEmail(requestId).getToken()).get());
//            chatRoom.addUser(userRoomsRepository.findById(getAuthenticInfo.GetToken()).get());
//            chatRoomRepository.save(chatRoom);
//            
//            System.out.println(chatRoom.toString());
//            if (optionalFriendRoom.isPresent()) {
//                FriendRoom friendRoom = optionalFriendRoom.get();
//
//                // 친구 요청 목록에서 해당 요청 제거
//                friendRoom.getRequestIds().remove(getAuthenticInfo.GetEmail());
//                FriendInfo friendInfo = new FriendInfo();
//                System.out.println(requestId);
//                friendInfo.setToken(userService.getUserByEmail(getAuthenticInfo.GetEmail()).getToken());
//                friendInfo.setRoomName(getAuthenticInfo.GetEmail());
//                friendInfo.setChatRoom(chatRoom);
//                friendRoom.getFriendIds().add(friendInfo);
//                friendRoomRepository.save(friendRoom);
//                // 친구 목록에 요청한 이메일 추가
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//            
//            if (optionalMyFriendRoom.isPresent()) {
//                FriendRoom friendRoom = optionalMyFriendRoom.get();
//
//                // 친구 요청 목록에서 해당 요청 제거
//                friendRoom.getRequestIds().remove(requestId);
//                FriendInfo friendInfo = new FriendInfo();
//                friendInfo.setToken(userService.getUserByEmail(requestId).getToken());
//                friendInfo.setRoomName(requestId);
//                friendInfo.setChatRoom(chatRoom);
//                friendRoom.getFriendIds().add(friendInfo);
//                friendRoomRepository.save(friendRoom);
//                // 친구 목록에 요청한 이메일 추가
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok("친구 요청을 수락하였습니다.");
//        } catch (Exception e) {
//        	e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("친구 요청 수락 중 오류가 발생하였습니다.");
//        }
//    }
//    
//    //친구요청 거절
//    @PostMapping(value = "/api/rejectFriendRequest", produces = "text/plain;charset=UTF-8")
//    public ResponseEntity<String> rejectFriendRequest(@RequestBody ApproveFriendRequest approveRequest) {
//        try {
//            String requestId = approveRequest.getRequestId();
//            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(getAuthenticInfo.GetEmail());
//            
//            if (optionalFriendRoom.isPresent()) {
//                FriendRoom friendRoom = optionalFriendRoom.get();
//                friendRoom.getRequestIds().remove(requestId);
//                friendRoomRepository.save(friendRoom);
//                return ResponseEntity.ok("친구 요청을 거절하였습니다.");
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("친구 요청 거절 중 오류가 발생하였습니다.");
//        }
//    }
//    
// // GET 요청을 처리하여 현재 사용자의 이메일을 반환하는 메서드
//    @GetMapping(value = "/api/getCurrentUserEmail", produces = "text/plain;charset=UTF-8")
//    public ResponseEntity<String> getCurrentUserEmail(HttpServletRequest request) {
//        try {
//            // 사용자 정보를 추출하는 방식에 따라 Principal 객체를 통해 가져오거나 직접 처리합니다.
//            Principal principal = request.getUserPrincipal();
//            System.out.println("principal : " + principal);
//            String userEmail = principal.getName(); // 예시: Principal에서 이메일 가져오기
//            System.out.println("userEmail : " + userEmail);
//
//            // 여기서 userEmail 변수에는 현재 사용자의 이메일이 포함되어야 합니다.
//
//            if (userEmail != null && !userEmail.isEmpty()) {
//                // 이메일이 유효하면 200 OK 응답을 반환하고 사용자 이메일을 body에 포함시킵니다.
//            	System.out.println("1111111111111111111111111");
//                return ResponseEntity.ok().body(userEmail);
//            } else {
//                // 이메일을 가져오지 못하거나 유효하지 않은 경우, 500 Internal Server Error 응답을 반환합니다.
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일을 못가져왔음!!!!!!!!!!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 예외가 발생하면 콘솔에 에러를 출력하고, 적절한 오류 메시지와 함께 500 Internal Server Error 응답을 반환합니다.
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occurred: " + e.getMessage());
//        }
//    }
//    
// // 친구 중복 요청 막기
//    @PostMapping(value = "/api/checkFriendship", produces = "text/plain;charset=UTF-8")
//    public ResponseEntity<String> checkFriendship(@RequestBody ApproveFriendRequest approveRequest) {
//        try {
//            String requestId = approveRequest.getRequestId();
//            System.out.println("requestId : " + requestId);
//            
//            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(getAuthenticInfo.GetEmail());
//            System.out.println("optionalFriendRoom : " + optionalFriendRoom.toString());
//            
//            if (optionalFriendRoom.isPresent()) {
//                FriendRoom friendRoom = optionalFriendRoom.get();
//                
//                boolean isFriend = friendRoom.getFriendIds().stream()
//                                    .anyMatch(friendInfo -> friendInfo.getRoomName().equals(requestId));
//                System.out.println("isFriend : " + isFriend);
//                
//                // 'true' 또는 'false' 문자열로 반환
//                return ResponseEntity.ok().body(Boolean.toString(isFriend));
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
//        }
//    }
//}
