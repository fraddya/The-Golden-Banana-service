package com.fraddy.goldenbanana.mapper;

import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import com.fraddy.goldenbanana.dto.request.user.UserCreateRequest;
import com.fraddy.goldenbanana.dto.request.user.UserSearchRequest;
import com.fraddy.goldenbanana.dto.request.user.UserUpdateRequest;
import com.fraddy.goldenbanana.dto.response.user.UserCreateResponse;
import com.fraddy.goldenbanana.dto.response.user.UserSearchResponse;
import com.fraddy.goldenbanana.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",imports = {Status.class})
public interface UserMapper {

    User mapToUser(UserCreateRequest request);

    User mapToUserUpdate(UserUpdateRequest request);

    UserCriteria mapToCriteria(UserSearchRequest request);

    List<UserSearchResponse> mapToSearchResponse(List<User> content);

    UserSearchResponse mapToUserViewResponse(User user);

    UserCreateResponse mapToUpdateResponse(User userUpdate);

}
