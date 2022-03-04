package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.CommentRequestDto;
import com.hanghae.naegahama.dto.message.MessageRequestDto;
import com.hanghae.naegahama.dto.room.RoomResponseDto;
import com.hanghae.naegahama.handler.ex.RoomNotFoundException;
import com.hanghae.naegahama.repository.MessageRepository;
import com.hanghae.naegahama.repository.RoomRepository;
import com.hanghae.naegahama.repository.UserEnterRoomRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

    private final ChatService chatService;
    private final RoomRepository roomRepository;
    private final UserEnterRoomRepository userEnterRoomRepository;

    private final MessageRepository messageRepository;

    public ResponseEntity<?> createRoom(String name, Post post) {
        //포스트 생성과 동시에 만들어진다.
        roomRepository.save(new Room(name, post));
        //채팅방과 유저의 연관관계 맺어주기
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> getRooms(User user) {
        // 들어온 유저가 해당하는 모든 방들 다꺼내기
        List<UserEnterRoom> enterRooms = userEnterRoomRepository.findByUserOrderByIdDesc(user);
        log.info(enterRooms.toString());
        List<RoomResponseDto> Rooms = new ArrayList<>();
        log.info("여기까지 된다");
        for (UserEnterRoom enterRoom : enterRooms) {
            Rooms.add(new RoomResponseDto(enterRoom.getRoom(),user));
        }
        return ResponseEntity.ok().body(Rooms);
    }



    public Page<Message> getChatMessageByRoomId(Long roomId, Pageable pageable) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("에라 모르겠다")
        );
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 150);
        return messageRepository.findByRoom(room, pageable);
    }

    public RoomResponseDto getEachChatRoom(Long roomId, User user) {
        Room chatRoom = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("찾는 채팅방이 존재하지 않습니다.")
        );
        RoomResponseDto roomResponseDto = new RoomResponseDto(chatRoom, user);
        return roomResponseDto;
    }

    public ResponseEntity<?> outChatRoom(Long roomId, User user) {
        Optional<Room> tmp = roomRepository.findById(roomId);
        if(!tmp.isPresent()){
            return ResponseEntity.badRequest().body(new IllegalArgumentException("그런 방은 존재하지 않습니다."));
        }
        Room chatRoom = tmp.get();
        userEnterRoomRepository.deleteByUserAndRoom(user,chatRoom);
        chatService.messageResolver(new MessageRequestDto(MessageType.QUIT,roomId,user.getId(),user.getNickName(),""));
        return ResponseEntity.ok().body("채팅방 나가기 성공!");
    }

    public ResponseEntity<?> enterRoom(User user, Long roomId) {
        Room findRoom = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("존재하지 않는 방입니다.")
        );
        UserEnterRoom findUserRoom = userEnterRoomRepository.findByUserAndRoom(user, findRoom);
        if(findUserRoom == null) {
            userEnterRoomRepository.save(new UserEnterRoom(user, findRoom, RoomUserStatus.ENTER));
            log.info("저장성공 user = {}, room = {}", user.getNickName(), findRoom.getName());
        }
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }
}
