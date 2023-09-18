package com.scrabble.api.controller;

import com.scrabble.api.dto.*;
import com.scrabble.api.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
@CrossOrigin(origins = "http://localhost:63342")
public class ScoreController {

    private ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/rules")
    public ResponseEntity<ScoringRuleResponseDto> getScoringRules() {
        return ResponseEntity.ok(scoreService.getScoringRules());
    }

    @PostMapping
    public ResponseEntity<SaveScoreResponseDto> saveScore(@RequestBody SaveScoreRequestDto saveScoreRequestDto) {
        return ResponseEntity.ok(scoreService.saveScore(saveScoreRequestDto));
    }

    @GetMapping("/top-scores")
    public ResponseEntity<TopScoresResponseDto> getTopScores() {
        return ResponseEntity.ok(scoreService.getTopScores());
    }

}
