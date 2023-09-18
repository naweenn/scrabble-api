package com.scrabble.api.dto;

import com.scrabble.api.enums.ResponseStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
    private ResponseStatus status;
    private String message;
}
