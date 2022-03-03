package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Room;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserEnterRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEnterRoomRepository extends JpaRepository<UserEnterRoom, Long> {
    List<UserEnterRoom> findByUserOrderByIdDesc(User user);
    void deleteByUserAndRoom(User user, Room room);
}
