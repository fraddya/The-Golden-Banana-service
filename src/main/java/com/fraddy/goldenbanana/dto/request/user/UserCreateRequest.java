package com.fraddy.goldenbanana.dto.request.user;


import com.fraddy.goldenbanana.enums.GenderType;
import com.fraddy.goldenbanana.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotNull(message = "FirstName is required.")
    private String firstName;

    @NotNull(message = "LastName is required.")
    private String lastName;

    @NotNull(message = "Contact No is required.")
    private String contactNo;

    private String dateJoin;

    @NotNull(message = "Gender Type is required.")
    private GenderType genderType;

    private String image;
    private String email;
    private String userLogging;
    private String passWord;
    private UserType role;
}
