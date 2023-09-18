package com.scrabble.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
public class ScoreHistory {

    @Id
    private String word;

    private int score;

    @CreatedDate
    private Date createdDate;

}
