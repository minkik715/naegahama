package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByRoomId(String roomId, Pageable pageable);
}
