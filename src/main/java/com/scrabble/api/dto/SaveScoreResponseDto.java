package com.scrabble.api.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class SaveScoreResponseDto extends ResponseDto {
    private String word;
    private int score;

}
