package com.scrabble.api.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ScoreDto {
    private String word;
    private int score;
}
