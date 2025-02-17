package com.fraddy.goldenbanana.service;

import com.fraddy.goldenbanana.config.GoldenBananaConfig;
import com.fraddy.goldenbanana.dto.response.userLevelProgress.QuestionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "theGoldenBanana", value = "theGoldenBanana/api", url = "${app.externalEndpoint.baseUrl}", configuration = GoldenBananaConfig.class)
public interface FeignClientService {

    @GetMapping("${app.endpoint.requestQuestionFromApi}")
    ResponseEntity<QuestionResponse> getQuestion();
}
