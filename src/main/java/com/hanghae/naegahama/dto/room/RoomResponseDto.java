package com.hanghae.naegahama.dto.room;

import com.hanghae.naegahama.domain.Room;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RoomResponseDto {
    private Long roomId;
    private String roomName;
    private String latestMessage;

    private String category;

    public RoomResponseDto(Room room,User user) {
        this.roomId = room.getId();
        this.roomName = room.getName();
        this.latestMessage = room.getModifiedAt().toString();
        this.category = room.getPost().getCategory();
    }


}
