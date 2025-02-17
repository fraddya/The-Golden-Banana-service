package com.fraddy.goldenbanana.dto.request.level;


import com.fraddy.goldenbanana.enums.LevelDifficultyType;
import lombok.Data;

@Data
public class LevelCreateRequest {
    private String name;
    private String description;
    private LevelDifficultyType difficulty;
}
