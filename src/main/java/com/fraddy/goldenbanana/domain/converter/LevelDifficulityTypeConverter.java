package com.fraddy.goldenbanana.domain.converter;


import com.fraddy.goldenbanana.enums.LevelDifficultyType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LevelDifficulityTypeConverter implements AttributeConverter<LevelDifficultyType, String> {

    @Override
    public String convertToDatabaseColumn(LevelDifficultyType userType) {
        return userType != null ? userType.getValue() : null;
    }

    @Override
    public LevelDifficultyType convertToEntityAttribute(String s) {
        return s != null ? LevelDifficultyType.getEnum(s) : null;
    }

}
