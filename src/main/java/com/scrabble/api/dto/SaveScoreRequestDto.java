package com.scrabble.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class SaveScoreRequestDto {

    @NotNull(message = "Word can not be null.")
    @NotEmpty(message = "Word can not be empty.")
    @Size(min = 1, max = 10, message = "Length of the word must be between 1 to 10.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Word should only contain letters.")
    private String word;

}
