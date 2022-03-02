//package com.hanghae.naegahama.service;
//
//<<<<<<< HEAD
//import com.hanghae.naegahama.domain.Room;
//import com.hanghae.naegahama.domain.User;
//import com.hanghae.naegahama.repository.RoomRepository;
//import com.hanghae.naegahama.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.Optional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class RoomService {
//
//    private final RoomRepository roomRepository;
//    private final UserService userService;
//
//    private final UserRepository userRepository;
//    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId 와 채팅룸 id 를 맵핑한 정보 저장
//
//    // 채팅방 생성
//    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto) {
//        ChatRoom chatRoom = new ChatRoom(requestDto, userService);
//        chatRoomRepository.save(chatRoom);
//        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom, userService.findById(requestDto.getUserId()));
//        return chatRoomResponseDto;
//    }
//
//    // 유저가 들어가있는 전체 채팅방 조회
//    public List<ChatRoomListDto> getAllChatRooms(User user) {
//        List<>
//        for (ChatRoom chatRoom : chatRoomRepository.findAllByOrderByCreatedAtDesc()) {
//        }
//
//
//        List<ChatRoomListDto> userChatRoom = new ArrayList<>();
//        for (ChatRoom chatRoom : chatRoomRepository.findAllByOrderByCreatedAtDesc()) {
//            if (chatRoom.getUserList().contains(user)) {
//                userChatRoom.add(new ChatRoomListDto(chatRoom, chatRoom.getUserList().get(0)));
//            }
//        }
//        return userChatRoom;
//    }
//
//
//    // 개별 채팅방 조회
//    public ChatRoomResponseDto getEachChatRoom(Long id, User user) {
//        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("찾는 채팅방이 존재하지 않습니다.")
//        );
//        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom, user);
//        return chatRoomResponseDto;
//    }
//
//
//    @Transactional
//    public ResponseEntity<?> inviteUser(InvitationDto invitationDto) {
//        Long roomId = invitationDto.getRoomId();
//        String email = invitationDto.getEmail();
//        Optional<User> tmp = userRepository.findByEmail(email);
//        if (!tmp.isPresent()) {
//            return ResponseEntity.badRequest().body(new IllegalArgumentException("이메일 정보를 확인해주세요"));
//        }
//        User user = tmp.get();
//        ChatRoom findRoom = chatRoomRepository.findById(roomId).orElseThrow(
//                () -> new IllegalArgumentException("방번호를 확인해주세요")
//        );
//        findRoom.getUserList().add(user);
//        chatRoomRepository.save(findRoom);
//        return ResponseEntity.ok().body("good");
//
//    }
//
//
//    /*@Transactional
//    public ResponseEntity<?> outChatRoom(Long roomId, User user) {
//        Optional<Room> tmp = roomRepository.findById(roomId);
//        if (!tmp.isPresent()) {
//            return ResponseEntity.badRequest().body(new IllegalArgumentException("그런 방은 존재하지 않습니다."));
//        }
//        Room chatRoom = tmp.get();
//
//        for (User usr : chatRoom.getUserList()) {
//            if (usr.getId().equals(user.getId())) {
//                chatRoom.getUserList().remove(usr);
//                break;
//            }
//        }
//        chatRoomRepository.save(chatRoom);
//        return ResponseEntity.ok().body("채팅방 나가기 성공!");
//    }
//
//    public class RoomService {
//
//    }*/
//}
