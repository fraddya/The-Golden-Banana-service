package com.fraddy.goldenbanana.dto.response.userLevelProgress;


import com.fraddy.goldenbanana.enums.GenderType;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class UserLevelProgressViewResponse {
    private Long id;
    private BigInteger marks;
    private BigInteger timeInSeconds;

    private UserData user;
    private LevelData level;

    @Data
    public static class UserData {
        private Long id;
        private String firstName;
        private String lastName;
        private String contactNo;
        private LocalDate dateJoin;
        private GenderType genderType;
        private String email;
    }

    @Data
    public static class LevelData {
        private Long id;
        private String name;
        private String description;
    }
}
