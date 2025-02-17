package com.fraddy.goldenbanana.dto.request.userLevelProgress;


import lombok.Data;

import java.math.BigInteger;

@Data
public class UserLevelProgressCreateRequest {
    private BigInteger marks;
    private BigInteger timeInSeconds;

    private UserData user;
    private LevelData level;

    @Data
    public static class UserData {
        private Long id;
    }

    @Data
    public static class LevelData {
        private Long id;
    }
}
