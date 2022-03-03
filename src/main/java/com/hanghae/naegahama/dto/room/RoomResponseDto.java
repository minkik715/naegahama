package com.hanghae.naegahama.dto.room;

import com.hanghae.naegahama.domain.Room;
import com.hanghae.naegahama.domain.User;

import java.time.LocalDateTime;

public class RoomResponseDto {
    private Long id;
    private String chatRoomName;
    private String modifiedAt;

    private String username;

    public RoomResponseDto(Room room,User user) {
        this.id = room.getId();
        this.chatRoomName = room.getName();
        this.modifiedAt = room.getModifiedAt().toString();
        this.username = user.getNickName();
    }


}
