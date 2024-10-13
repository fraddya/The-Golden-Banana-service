package com.fraddy.goldenbanana.mapper;

import com.fraddy.goldenbanana.domain.UserLevelProgress;
import com.fraddy.goldenbanana.domain.criteria.UserLevelProgressCriteria;
import com.fraddy.goldenbanana.dto.request.userLevelProgress.UserLevelProgressCreateRequest;
import com.fraddy.goldenbanana.dto.request.userLevelProgress.UserLevelProgressSearchRequest;
import com.fraddy.goldenbanana.dto.response.userLevelProgress.UserLevelProgressViewResponse;
import com.fraddy.goldenbanana.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",imports = {Status.class})
public interface UserLevelProgressMapper {

    UserLevelProgress mapToUserLevelProgress(UserLevelProgressCreateRequest request);

    UserLevelProgressViewResponse mapToViewResponse(UserLevelProgress userLevelProgress1);

    UserLevelProgressCriteria mapToCriteria(UserLevelProgressSearchRequest request);

    List<UserLevelProgressViewResponse> mapToSearchResponse(List<UserLevelProgress> content);
}
