package com.fraddy.goldenbanana.domain.criteria;

import com.fraddy.goldenbanana.domain.User;
import lombok.Data;

@Data
public class UserCriteria extends User {

    private String email;

    private String passWord;

    private String keyword;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;

}
