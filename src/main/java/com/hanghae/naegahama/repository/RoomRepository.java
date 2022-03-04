package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByOrderByCreatedAtDesc();
}
