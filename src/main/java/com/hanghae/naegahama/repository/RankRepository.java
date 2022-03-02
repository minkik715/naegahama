package com.hanghae.naegahama.repository;

import com.hanghae.naegahama.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank, Long> {
    List<Rank> findAllByOrderByRankAsc();
}
