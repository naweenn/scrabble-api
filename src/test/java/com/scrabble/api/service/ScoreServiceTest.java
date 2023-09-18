package com.scrabble.api.service;

import com.scrabble.api.dto.SaveScoreRequestDto;
import com.scrabble.api.dto.SaveScoreResponseDto;
import com.scrabble.api.dto.ScoringRuleResponseDto;
import com.scrabble.api.dto.TopScoresResponseDto;
import com.scrabble.api.entity.ScoreHistory;
import com.scrabble.api.enums.ResponseStatus;
import com.scrabble.api.repository.ScoreHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScoreServiceTest {

    @InjectMocks
    ScoreService scoreService;

    @Mock
    private ScoreHistoryRepository scoreHistoryRepository;

    @Value("${score.rules}")
    private String scoringRulesJsonArray;

    @Test
    void getScoringRulesMethodShouldReturnRules() {
        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", scoringRulesJsonArray);
        ScoringRuleResponseDto responseDto = scoreService.getScoringRules();
        assertEquals(ResponseStatus.SUCCESS, responseDto.getStatus());
        assertEquals(26, responseDto.getScoringRules().size());
    }

    @Test
    void getScoringRulesMethodShouldReturnErrorWhenFailedToLoadRules() {
        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", "");
        ScoringRuleResponseDto responseDto = scoreService.getScoringRules();
        assertEquals(ResponseStatus.ERROR, responseDto.getStatus());
    }


    @Test
    void saveScoreMethodTestOnValidUserInput() {
        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", scoringRulesJsonArray);
        SaveScoreResponseDto responseDto = scoreService.saveScore(getSaveScoreRequestDto("testword"));
        verify(scoreHistoryRepository, times(1)).save(any(ScoreHistory.class));
        assertEquals(ResponseStatus.SUCCESS, responseDto.getStatus());
        assertEquals(12, responseDto.getScore());
    }

    @Test
    void saveScoreMethodTestOnInvalidUserInput() {
        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", scoringRulesJsonArray);
        SaveScoreResponseDto responseDto = scoreService.saveScore(getSaveScoreRequestDto("test word"));
        verify(scoreHistoryRepository, times(0)).save(any(ScoreHistory.class));
        assertEquals(ResponseStatus.ERROR, responseDto.getStatus());
    }

    @Test
    void getTopScoresWhenSavedScoresAreNotAvailable() {
        when(scoreHistoryRepository.findTop10ByOrderByScoreDesc()).thenReturn(List.of());
        TopScoresResponseDto responseDto = scoreService.getTopScores();
        assertEquals(ResponseStatus.NO_RECORDS_FOUND, responseDto.getStatus());
    }

    @Test
    void getTopScoresWhenSavedScoresAreAvailable() {
        when(scoreHistoryRepository.findTop10ByOrderByScoreDesc()).thenReturn(List.of(new ScoreHistory()));
        TopScoresResponseDto responseDto = scoreService.getTopScores();
        assertEquals(ResponseStatus.SUCCESS, responseDto.getStatus());
        assertEquals(1, responseDto.getTopScores().size());
    }

    @Test
    void getTopScoresWhenFailedToLoadScores() {
        when(scoreHistoryRepository.findTop10ByOrderByScoreDesc()).thenThrow(RuntimeException.class);
        TopScoresResponseDto responseDto = scoreService.getTopScores();
        assertEquals(ResponseStatus.ERROR, responseDto.getStatus());
    }

    @Test
    void processAndValidateWordMethodTestOnValidInput() {
        assertEquals("TEST",
                ReflectionTestUtils.invokeMethod(scoreService, "processAndValidateWord", "test"));
        assertEquals("TEST",
                ReflectionTestUtils.invokeMethod(scoreService, "processAndValidateWord", "  test  "));
        assertEquals("TEST",
                ReflectionTestUtils.invokeMethod(scoreService, "processAndValidateWord", "  teST"));
    }


    @Test
    void buildAndValidateWordMethodShouldThrowExceptionOnInvalidInputs() {

        Assertions.assertThrows(Exception.class,
                () ->  ReflectionTestUtils.invokeMethod(scoreService, "processAndValidateWord", ""));
        Assertions.assertThrows(Exception.class,
                () ->  ReflectionTestUtils.invokeMethod(scoreService, "processAndValidateWord", "   "));
        Assertions.assertThrows(Exception.class,
                () ->  ReflectionTestUtils.invokeMethod(scoreService, "processAndValidateWord", "TE ST"));
    }

    @Test
    void calculateScoreMethodTest() {
        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", scoringRulesJsonArray);
        int score = ReflectionTestUtils.invokeMethod(scoreService, "calculateScore", "TEST");
        assertEquals(4, score);
        score = ReflectionTestUtils.invokeMethod(scoreService, "calculateScore", "FLOWER");
        assertEquals(12, score);
        score = ReflectionTestUtils.invokeMethod(scoreService, "calculateScore", "flower");
        assertEquals(0, score);
    }

    private SaveScoreRequestDto getSaveScoreRequestDto(String word) {
        return SaveScoreRequestDto.builder()
                .word(word)
                .build();
    }

}