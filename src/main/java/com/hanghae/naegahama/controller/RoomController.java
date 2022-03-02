package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.domain.Message;
import com.hanghae.naegahama.domain.Room;
import com.hanghae.naegahama.dto.room.CreateRoomDto;
import com.hanghae.naegahama.dto.room.RoomResponseDto;
import com.hanghae.naegahama.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    // 각 유저에게 해당하는 room들의 정보 보내기
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> room(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomService.getRooms(userDetails.getUser());
    }

    //룸 만들기
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomDto createRoomDto){
        return roomService.createRoom(createRoomDto.getRoomName());
    }



    //하나의 룸에 해당하는 메세지 끌고오기
    @GetMapping("/rooms/{roomId}/messages")
    public Page<Message> getEachChatRoomMessages(@PathVariable String roomId, @PageableDefault Pageable pageable) {
        return roomService.getChatMessageByRoomId(roomId, pageable);
    }

    @GetMapping("/rooms/{roomId}")
    public RoomResponseDto getEachChatRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return roomService.getEachChatRoom(roomId,userDetails.getUser());
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> outChatRoom(@PathVariable Long roomId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomService.outChatRoom(roomId,userDetails.getUser());
    }



}