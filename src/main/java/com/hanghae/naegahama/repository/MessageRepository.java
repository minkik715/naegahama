package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
