package com.scrabble.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrabble.api.dto.SaveScoreRequestDto;
import com.scrabble.api.entity.ScoreHistory;
import com.scrabble.api.repository.ScoreHistoryRepository;
import com.scrabble.api.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
class ScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreService scoreService;

    @MockBean
    private ScoreHistoryRepository scoreHistoryRepository;

    @Value("${score.rules}")
    private String scoringRulesJsonArray;

    @Test
    void testGetScoringRulesEndpoint() throws Exception {

        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", scoringRulesJsonArray);

        mockMvc.perform(get("/api/score/rules"))
                .andExpect(status().isOk());

    }

    @Test
    void testSaveScoreEndpoint() throws Exception {
        ReflectionTestUtils.setField(scoreService, "scoringRulesJsonArray", scoringRulesJsonArray);

        mockMvc.perform(post("/api/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getSaveScoreRequestDto("testword")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTopScoresEndpoint() throws Exception {
        when(scoreHistoryRepository.findTop10ByOrderByScoreDesc()).thenReturn(List.of(new ScoreHistory()));

        mockMvc.perform(get("/api/score/top-scores"))
                .andExpect(status().isOk());
    }

    private SaveScoreRequestDto getSaveScoreRequestDto(String word) {
        return SaveScoreRequestDto.builder()
                .word(word)
                .build();
    }

}