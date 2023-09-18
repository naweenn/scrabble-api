package com.scrabble.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrabble.api.dto.*;
import com.scrabble.api.enums.ResponseStatus;
import com.scrabble.api.entity.ScoreHistory;
import com.scrabble.api.repository.ScoreHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ScoreService {

    @Value("${score.rules}")
    private String scoringRulesJsonArray;

    private ScoreHistoryRepository scoreHistoryRepository;

    @Autowired
    public ScoreService(ScoreHistoryRepository scoreHistoryRepository) {
        this.scoreHistoryRepository = scoreHistoryRepository;
    }

    public ScoringRuleResponseDto getScoringRules() {
        try {

            List<ScoringRuleDto> scoringRules
                    = new ObjectMapper().readValue(scoringRulesJsonArray, new TypeReference<>() {});

            return ScoringRuleResponseDto.builder()
                    .status(ResponseStatus.SUCCESS)
                    .scoringRules(scoringRules)
                    .build();

        } catch (Exception e) {
            log.info("Failed to retrieve scoring rules. Error: {}", e.getMessage());
            e.printStackTrace();

            return ScoringRuleResponseDto.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Failed to retrieve scoring rules. Please try again later.")
                    .build();
        }
    }

    public  SaveScoreResponseDto saveScore(SaveScoreRequestDto saveScoreRequestDto) {

        try {

            String word = processAndValidateWord(saveScoreRequestDto.getWord());

            ScoreHistory score = new ScoreHistory();
            score.setWord(word);
            score.setScore(calculateScore(word));
            scoreHistoryRepository.save(score);

            log.info("Successfully saved the score for the word: \"{}\" score: {}",
                    saveScoreRequestDto.getWord(), score.getScore());

            return SaveScoreResponseDto.builder()
                    .status(ResponseStatus.SUCCESS)
                    .message("Successfully saved the score")
                    .word(saveScoreRequestDto.getWord())
                    .score(score.getScore())
                    .build();

        } catch (Exception e) {
            log.info("Failed to saved the score for the word: \"{}\" error: {}",
                    saveScoreRequestDto.getWord(), e.getMessage());
            e.printStackTrace();

            return SaveScoreResponseDto.builder()
                    .status(ResponseStatus.ERROR)
                    .message(e.getMessage())
                    .word(saveScoreRequestDto.getWord())
                    .build();
        }
    }

    public TopScoresResponseDto getTopScores() {

        try {
            List<ScoreHistory> topScores = scoreHistoryRepository.findTop10ByOrderByScoreDesc();

            if(CollectionUtils.isEmpty(topScores)) {
                return TopScoresResponseDto.builder()
                        .status(ResponseStatus.NO_RECORDS_FOUND)
                        .message("No recorded scores found.")
                        .build();
            }

            List<ScoreDto> topScoreDtoList = new ArrayList<>();
            topScores.forEach(score -> topScoreDtoList.add(toScoreDto(score)));

            return TopScoresResponseDto.builder()
                    .status(ResponseStatus.SUCCESS)
                    .message("Top scores")
                    .topScores(topScoreDtoList)
                    .build();

        } catch (Exception e) {
            log.info("Failed to retrieve top scores. Error: {}", e.getMessage());
            e.printStackTrace();

            return TopScoresResponseDto.builder()
                    .status(ResponseStatus.ERROR)
                    .message("Failed to retrieve top scores. Please try again later.")
                    .build();
        }
    }

    private String processAndValidateWord(String word) throws Exception {
        if(!StringUtils.hasLength(word)) {
            throw new Exception("Word can not be null or empty.");
        }
        word = word.trim();
        word = word.toUpperCase();
        if(word.length() < 1) {
            throw new Exception("Word must have at least one character.");
        }
        if(word.contains(" ")) {
            throw new Exception("Word can not contain spaces.");
        }
        return word;
    }

    private int calculateScore(String word) throws JsonProcessingException {

        List<ScoringRuleDto> scoringRules
                = new ObjectMapper().readValue(scoringRulesJsonArray, new TypeReference<>() {});

        int score = 0;
        char[] letters = word.toCharArray();

        for (char letter : letters) {
            for (ScoringRuleDto scoreRule : scoringRules) {
                if (letter == scoreRule.getLetter()) {
                    score += scoreRule.getScore();
                    break;
                }
            }
        }

        return score;
    }

    private ScoreDto toScoreDto(ScoreHistory score) {
        ScoreDto scoreDto = new ScoreDto();
        BeanUtils.copyProperties(score, scoreDto);
        return scoreDto;
    }

}
