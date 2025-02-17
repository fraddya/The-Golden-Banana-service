package com.fraddy.goldenbanana.dto.request.user;


import com.fraddy.goldenbanana.domain.base.BaseSearchRequest;
import com.fraddy.goldenbanana.enums.GenderType;
import com.fraddy.goldenbanana.enums.Status;
import lombok.Data;

@Data
public class UserSearchRequest extends BaseSearchRequest {

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
    private String role;
    private Status status;

    private String sortProperty = "lastModifiedAt";

    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}
