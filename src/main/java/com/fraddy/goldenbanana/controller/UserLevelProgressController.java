package com.fraddy.goldenbanana.controller;


import com.fraddy.goldenbanana.domain.UserLevelProgress;
import com.fraddy.goldenbanana.domain.base.PagingListResponseWrapper;
import com.fraddy.goldenbanana.domain.base.SingleItemResponseWrapper;
import com.fraddy.goldenbanana.domain.criteria.UserLevelProgressCriteria;
import com.fraddy.goldenbanana.dto.request.userLevelProgress.UserLevelProgressCreateRequest;
import com.fraddy.goldenbanana.dto.request.userLevelProgress.UserLevelProgressSearchRequest;
import com.fraddy.goldenbanana.dto.response.userLevelProgress.UserLevelProgressViewResponse;
import com.fraddy.goldenbanana.mapper.UserLevelProgressMapper;
import com.fraddy.goldenbanana.service.UserLevelProgressService;
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
@Schema(name = "UserLevelProgressController", description = "create/search/view/update/delete")
public class UserLevelProgressController {

    @Autowired
    private UserLevelProgressService userService;

    @Autowired
    private UserLevelProgressMapper mapper;

    @PostMapping("${app.endpoint.userLevelProgresCreate}")
    public ResponseEntity<SingleItemResponseWrapper<UserLevelProgressViewResponse>> create(
            @Validated @RequestBody UserLevelProgressCreateRequest request) {

        UserLevelProgress userLevelProgress = mapper.mapToUserLevelProgress(request);

        UserLevelProgress userLevelProgress1 = userService.save(userLevelProgress);

        UserLevelProgressViewResponse response = mapper.mapToViewResponse(userLevelProgress1);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @PutMapping("${app.endpoint.userLevelProgresUpdate}")
    public ResponseEntity<SingleItemResponseWrapper<UserLevelProgressViewResponse>> update(
            @PathVariable Long id, @Validated @RequestBody UserLevelProgressCreateRequest request) {
        log.info("update Request: {}", request);

        UserLevelProgress userLevelProgress = mapper.mapToUserLevelProgress(request);

        userLevelProgress.setId(id);
        UserLevelProgress updatedUserLevelProgress = userService.update(userLevelProgress);

        UserLevelProgressViewResponse response = mapper.mapToViewResponse(updatedUserLevelProgress);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userLevelProgresSearch}")
    public ResponseEntity<PagingListResponseWrapper<UserLevelProgressViewResponse>> retrieve(
            @Validated UserLevelProgressSearchRequest request) {

        UserLevelProgressCriteria criteria = mapper.mapToCriteria(request);

        Page<UserLevelProgress> results = userService.search(criteria);

        List<UserLevelProgressViewResponse> responses = mapper.mapToSearchResponse(results.getContent());

        PagingListResponseWrapper.Pagination pagination =
                new PagingListResponseWrapper.Pagination(
                        results.getNumber() + 1,
                        results.getSize(),
                        results.getTotalPages(),
                        results.getTotalElements());

        return new ResponseEntity<>(new PagingListResponseWrapper<>(responses, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.userLevelProgresView}")
    public ResponseEntity<SingleItemResponseWrapper<UserLevelProgressViewResponse>> retrieve(
            @PathVariable Long id) {
        UserLevelProgress userLevelProgress = userService.retrieve(id);

        UserLevelProgressViewResponse response = mapper.mapToViewResponse(userLevelProgress);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.userLevelProgresDelete}")
    public ResponseEntity<SingleItemResponseWrapper<UserLevelProgressViewResponse>> delete(
            @PathVariable Long id) {

        UserLevelProgress userLevelProgress = userService.delete(id);

        UserLevelProgressViewResponse response = new UserLevelProgressViewResponse();

        if (userLevelProgress != null) {
            response = mapper.mapToViewResponse(userLevelProgress);
        }
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

}
