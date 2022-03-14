package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long>
{
    Achievement findByUser(User user);
}
