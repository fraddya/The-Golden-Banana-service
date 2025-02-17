package com.fraddy.goldenbanana.dto.request.user;


import com.fraddy.goldenbanana.enums.GenderType;
import com.fraddy.goldenbanana.enums.UserType;
import lombok.Data;

@Data
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    private String contactNo;

    private String dateJoin;

    private GenderType genderType;

    private String image;
    private String email;
    private String userLogging;
    private String passWord;
    private UserType role;
}
