package com.scrabble.api.repository;

import com.scrabble.api.entity.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, String> {

    List<ScoreHistory> findTop10ByOrderByScoreDesc();

}
