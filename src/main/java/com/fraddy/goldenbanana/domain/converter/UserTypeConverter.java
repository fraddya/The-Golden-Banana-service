package com.fraddy.goldenbanana.domain.converter;


import com.fraddy.goldenbanana.enums.UserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType) {
        return userType != null ? userType.getValue() : null;
    }

    @Override
    public UserType convertToEntityAttribute(String s) {
        return s != null ? UserType.getEnum(s) : null;
    }

}
