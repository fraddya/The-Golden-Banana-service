package com.fraddy.goldenbanana.domain.criteria;

import com.fraddy.goldenbanana.domain.User;
import lombok.Data;

@Data
public class UserLevelProgressCriteria extends User {

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;

}
