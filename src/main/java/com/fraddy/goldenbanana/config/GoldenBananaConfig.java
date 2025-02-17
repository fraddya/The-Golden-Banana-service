package com.fraddy.goldenbanana.config;

import com.fraddy.goldenbanana.intercepter.FeignClientInterceptor;
import org.springframework.context.annotation.Bean;

//@Configuration
public class GoldenBananaConfig {

    @Bean
    public FeignClientInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }
}
