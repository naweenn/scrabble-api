package com.scrabble.api.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class SaveScoreRequestDto {

    private String word;

}
