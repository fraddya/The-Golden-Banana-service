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

    Long storedSolution = null;

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

    //ToDo : requesting queastion
    /*@GetMapping("${app.endpoint.requestQuestion}")
    public ResponseEntity<SingleItemResponseWrapper<QuestionResponse>> requestQuestion() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://marcconrad.com/uob/banana/api.php?out=json";

        // Manually handle the response as String and convert it if necessary
        //ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        QuestionResponse questionResponse = new QuestionResponse();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            // Log response for debugging
            log.info("Response from API: {}", body);
            try {
                // Assuming the response is actually JSON despite the content-type header
                ObjectMapper mapper = new ObjectMapper();
                QuestionResponse questionResponse = mapper.readValue(body, QuestionResponse.class);

                storedSolution = questionResponse.getSolution();

                return new ResponseEntity<>(new SingleItemResponseWrapper<>(questionResponse), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                // Handle the case where the response is not valid JSON
                log.error("Failed to parse the response", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
