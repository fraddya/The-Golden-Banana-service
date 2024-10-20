package com.fraddy.goldenbanana.controller;


import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.base.PagingListResponseWrapper;
import com.fraddy.goldenbanana.domain.base.SingleItemResponseWrapper;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import com.fraddy.goldenbanana.dto.request.user.UserCreateRequest;
import com.fraddy.goldenbanana.dto.request.user.UserSearchRequest;
import com.fraddy.goldenbanana.dto.request.user.UserUpdateRequest;
import com.fraddy.goldenbanana.dto.response.user.UserCreateResponse;
import com.fraddy.goldenbanana.dto.response.user.UserSearchResponse;
import com.fraddy.goldenbanana.mapper.UserMapper;
import com.fraddy.goldenbanana.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Schema(name = "EmployeeController", description = "create/search/view/update/delete")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("${app.endpoint.userCreate}")
    public ResponseEntity<SingleItemResponseWrapper<UserCreateResponse>> create(
            @Validated @RequestBody UserCreateRequest request) {

        User user = userMapper.mapToUser(request);

        User user1 = userService.save(user);

        UserCreateResponse response = userMapper.mapToUpdateResponse(user1);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @PutMapping("${app.endpoint.userUpdate}")
    public ResponseEntity<SingleItemResponseWrapper<UserCreateResponse>> update(
            @PathVariable Long id, @Validated @RequestBody UserUpdateRequest request) {
        log.info("update Request: {}", request);

        User employee = userMapper.mapToUserUpdate(request);

        employee.setId(id);
        User updateEmployee = userService.update(employee);

        UserCreateResponse response = userMapper.mapToUpdateResponse(updateEmployee);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userSearch}")
    public ResponseEntity<PagingListResponseWrapper<UserSearchResponse>> retrieve(
            @Validated UserSearchRequest request) {

        UserCriteria criteria = userMapper.mapToCriteria(request);

        Page<User> results = userService.search(criteria);

        List<UserSearchResponse> responses = userMapper.mapToSearchResponse(results.getContent());

        PagingListResponseWrapper.Pagination pagination =
                new PagingListResponseWrapper.Pagination(
                        results.getNumber() + 1,
                        results.getSize(),
                        results.getTotalPages(),
                        results.getTotalElements());

        return new ResponseEntity<>(new PagingListResponseWrapper<>(responses, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userView}")
    public ResponseEntity<SingleItemResponseWrapper<UserSearchResponse>> retrieve(
            @PathVariable Long id) {
        User employee = userService.retrieve(id);

        UserSearchResponse response = userMapper.mapToUserViewResponse(employee);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.userDelete}")
    public ResponseEntity<SingleItemResponseWrapper<UserCreateResponse>> delete(
            @PathVariable Long id) {

        User employee = userService.delete(id);

        UserCreateResponse response = new UserCreateResponse();

        if (employee != null) {
            response = userMapper.mapToUpdateResponse(employee);
        }
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @PutMapping("${app.endpoint.userLogIn}")
    public ResponseEntity<SingleItemResponseWrapper<UserSearchResponse>> logIn(
            @Validated @RequestBody UserUpdateRequest request) {
        User user = userService.logIn(userMapper.mapToUserUpdate(request));
        UserSearchResponse response = userMapper.mapToUserViewResponse(user);
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userValidate}")
    public String validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return "Token is valid";
    }
}
