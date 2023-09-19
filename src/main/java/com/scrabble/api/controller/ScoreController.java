package com.scrabble.api.controller;

import com.scrabble.api.dto.*;
import com.scrabble.api.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScoreController {

    private ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/score/rules")
    public ResponseEntity<ScoringRuleResponseDto> getScoringRules() {
        return ResponseEntity.ok(scoreService.getScoringRules());
    }

    @PostMapping("/score")
    public ResponseEntity<SaveScoreResponseDto> saveScore(@Valid @RequestBody SaveScoreRequestDto saveScoreRequestDto) {
        return ResponseEntity.ok(scoreService.saveScore(saveScoreRequestDto));
    }

    @GetMapping("/score/top-scores")
    public ResponseEntity<TopScoresResponseDto> getTopScores() {
        return ResponseEntity.ok(scoreService.getTopScores());
    }

}
