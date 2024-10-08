package com.fraddy.goldenbanana.controller;


import com.fraddy.goldenbanana.domain.Level;
import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.base.PagingListResponseWrapper;
import com.fraddy.goldenbanana.domain.base.SingleItemResponseWrapper;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import com.fraddy.goldenbanana.dto.request.level.LevelCreateRequest;
import com.fraddy.goldenbanana.dto.request.user.UserCreateRequest;
import com.fraddy.goldenbanana.dto.request.user.UserSearchRequest;
import com.fraddy.goldenbanana.dto.request.user.UserUpdateRequest;
import com.fraddy.goldenbanana.dto.response.level.LevelSearchResponse;
import com.fraddy.goldenbanana.dto.response.user.UserCreateResponse;
import com.fraddy.goldenbanana.dto.response.user.UserSearchResponse;
import com.fraddy.goldenbanana.mapper.LevelMapper;
import com.fraddy.goldenbanana.mapper.UserMapper;
import com.fraddy.goldenbanana.service.LevelService;
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
public class LevelController {

    @Autowired
    private LevelService levelService;

    @Autowired
    private LevelMapper levelMapper;

    @PostMapping("${app.endpoint.userCreate}")
    public ResponseEntity<SingleItemResponseWrapper<LevelSearchResponse>> create(
            @Validated @RequestBody LevelCreateRequest request) {

        Level level = levelMapper.mapToLevel(request);

        Level level1 = levelService.save(level);

        LevelSearchResponse response = levelMapper.mapToViewResponse(level1);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @PutMapping("${app.endpoint.userUpdate}")
    public ResponseEntity<SingleItemResponseWrapper<LevelSearchResponse>> update(
            @PathVariable Long id, @Validated @RequestBody LevelCreateRequest request) {
        log.info("update Request: {}", request);

        Level level = levelMapper.mapToLevel(request);

        level.setId(id);
        Level updateLevel = levelService.update(level);

        LevelSearchResponse response = levelMapper.mapToViewResponse(updateLevel);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    /*@GetMapping("${app.endpoint.userSearch}")
    public ResponseEntity<PagingListResponseWrapper<LevelSearchResponse>> getAll(LevelCreateRequest request) {

        Level level = levelMapper.mapToLevel(request);
        List<Level> results = levelService.search(request);

        List<UserSearchResponse> responses = userMapper.mapToSearchResponse(results.getContent());

        PagingListResponseWrapper.Pagination pagination =
                new PagingListResponseWrapper.Pagination(
                        results.getNumber() + 1,
                        results.getSize(),
                        results.getTotalPages(),
                        results.getTotalElements());

        return new ResponseEntity<>(new PagingListResponseWrapper<>(responses, pagination), HttpStatus.OK);
    }*/

    @GetMapping("${app.endpoint.userView}")
    public ResponseEntity<SingleItemResponseWrapper<LevelSearchResponse>> retrieve(
            @PathVariable Long id) {
        Level level = levelService.retrieve(id);

        LevelSearchResponse response = levelMapper.mapToViewResponse(level);

        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.userDelete}")
    public ResponseEntity<SingleItemResponseWrapper<LevelSearchResponse>> delete(
            @PathVariable Long id) {

        Level level = levelService.delete(id);

        LevelSearchResponse response = new LevelSearchResponse();

        if (level != null) {
            response = levelMapper.mapToViewResponse(level);
        }
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(response), HttpStatus.OK);
    }

}
