package com.scrabble.api.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ScoringRuleDto {
    private char letter;
    private int score;
}
