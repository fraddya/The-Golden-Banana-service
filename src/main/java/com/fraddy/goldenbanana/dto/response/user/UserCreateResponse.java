package com.fraddy.goldenbanana.dto.response.user;

import com.fraddy.goldenbanana.enums.GenderType;
import com.fraddy.goldenbanana.enums.Status;
import com.fraddy.goldenbanana.enums.UserType;
import lombok.Data;

@Data
public class UserCreateResponse {

    private Long id;

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
    private Boolean firstTimeLogin;

    private String token;
    private String refreshToken;

    private Status status;
}
