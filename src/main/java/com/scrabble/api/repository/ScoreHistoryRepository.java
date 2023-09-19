package com.scrabble.api.repository;

import com.scrabble.api.entity.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, String> {

    List<ScoreHistory> findTop10ByOrderByScoreDesc();

}
