package com.fraddy.goldenbanana.domain.base;


import com.fraddy.goldenbanana.enums.RestApiResponseStatus;

public class ExceptionResponseWrapper extends BaseResponseWrapper {
    public ExceptionResponseWrapper(String message) {
        super(RestApiResponseStatus.NOT_FOUND);
        this.message=message;
    }
}
