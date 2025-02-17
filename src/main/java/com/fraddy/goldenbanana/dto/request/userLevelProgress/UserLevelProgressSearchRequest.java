package com.fraddy.goldenbanana.dto.request.userLevelProgress;


import com.fraddy.goldenbanana.domain.base.BaseSearchRequest;
import lombok.Data;

@Data
public class UserLevelProgressSearchRequest extends BaseSearchRequest {

    private Long userId;
    private Long levelId;

    private String sortProperty = "lastModifiedAt";

    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}
