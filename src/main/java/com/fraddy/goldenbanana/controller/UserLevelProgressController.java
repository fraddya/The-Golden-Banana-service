package com.fraddy.goldenbanana.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraddy.goldenbanana.domain.UserLevelProgress;
import com.fraddy.goldenbanana.domain.base.ListResponseWrapper;
import com.fraddy.goldenbanana.domain.base.PagingListResponseWrapper;
import com.fraddy.goldenbanana.domain.base.SingleItemResponseWrapper;
import com.fraddy.goldenbanana.domain.criteria.UserLevelProgressCriteria;
import com.fraddy.goldenbanana.dto.request.userLevelProgress.UserLevelProgressCreateRequest;
import com.fraddy.goldenbanana.dto.request.userLevelProgress.UserLevelProgressSearchRequest;
import com.fraddy.goldenbanana.dto.response.userLevelProgress.QuestionResponse;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Slf4j
@RestController
@Schema(name = "UserLevelProgressController", description = "create/search/view/update/delete")
public class UserLevelProgressController {

    @Autowired
    private UserLevelProgressService userService;

    @Autowired
    private UserLevelProgressMapper mapper;

    /*@Autowired
    private FeignClientService feignClientService;*/

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

    @GetMapping("${app.endpoint.userLevelProgresLeaderBord}")
    public ResponseEntity<ListResponseWrapper<UserLevelProgressViewResponse>> leaderBoard() {
        List<UserLevelProgress> userLevelProgresses = userService.leaderBoard();
        List<UserLevelProgressViewResponse> responses = mapper.mapToLeaderBoardResponse(userLevelProgresses);
        return new ResponseEntity<>(new ListResponseWrapper<>(responses), HttpStatus.OK);
    }

    //ToDo : requesting queastion
    @CrossOrigin(origins = "*")  // Allow all origins, or specify your domain instead of "*"
    @GetMapping("${app.endpoint.requestQuestion}")
    public ResponseEntity<SingleItemResponseWrapper<QuestionResponse>> requestQuestion() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        // Updated URL to use HTTPS
        String url = "https://marcconrad.com/uob/banana/api.php";

        // Fetch the API response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // Parse the response body into the QuestionResponse object
        QuestionResponse questionResponse = new QuestionResponse();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Assuming response body is in JSON format
            String jsonResponse = responseEntity.getBody();
            if (jsonResponse != null) {
                // Parse the JSON response (using your preferred library, e.g., Jackson)
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                // Set values in questionResponse
                questionResponse.setQuestion(jsonNode.get("question").asText());
                questionResponse.setSolution((long) jsonNode.get("solution").asInt());
            }
        }

        // Wrap and return the response
        return new ResponseEntity<>(new SingleItemResponseWrapper<>(questionResponse), HttpStatus.OK);
    }
}
