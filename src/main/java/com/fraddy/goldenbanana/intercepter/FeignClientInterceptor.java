package com.fraddy.goldenbanana.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*@Slf4j
@Component*/
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer ";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (RequestContextHolder.getRequestAttributes() != null && RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String authorization = request.getHeader(AUTHORIZATION_HEADER);
            if (StringUtils.isNotBlank(authorization)) {
                String token = authorization.substring(7);
                requestTemplate.header(AUTHORIZATION_HEADER, TOKEN_TYPE + token);
            }
        }

    }
}
