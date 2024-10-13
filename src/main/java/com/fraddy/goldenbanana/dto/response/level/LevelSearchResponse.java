package com.fraddy.goldenbanana.dto.response.level;


import com.fraddy.goldenbanana.enums.LevelDifficultyType;
import com.fraddy.goldenbanana.enums.Status;
import lombok.Data;

@Data
public class LevelSearchResponse {
    private Long id;
    private String name;
    private String description;
    private LevelDifficultyType difficulty;
    private Status status;
}
