package com.fraddy.goldenbanana.enums;

public enum LevelDifficultyType {
    EASY("EASY", "EASY"),
    MID("MID", "MID"),
    HARD("HARD", "HARD"),
    EPILOGUE("EPILOGUE", "EPILOGUE");

    private String label;

    private String value;

    LevelDifficultyType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static LevelDifficultyType getEnum(String s) {
        for (LevelDifficultyType item : LevelDifficultyType.values()) {
            if (item.value.equals(s)) {
                return item;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
    public String getLabel() {
        return label;
    }
}
