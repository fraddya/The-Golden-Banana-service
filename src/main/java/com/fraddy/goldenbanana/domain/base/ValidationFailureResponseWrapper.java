package com.fraddy.goldenbanana.domain.base;



import com.fraddy.goldenbanana.enums.RestApiResponseStatus;

import java.util.Collections;
import java.util.List;


public class ValidationFailureResponseWrapper extends BaseResponseWrapper {

    private List<ValidationFailure> ValidationFailures;

    public ValidationFailureResponseWrapper(List<ValidationFailure> ValidationFailures) {
        super(RestApiResponseStatus.VALIDATION_FAILURE);
        this.ValidationFailures = ValidationFailures;
    }

    public ValidationFailureResponseWrapper(String field, String code) {
        super(RestApiResponseStatus.VALIDATION_FAILURE);
        ValidationFailure vf = new ValidationFailure(field, code);
        this.ValidationFailures = Collections.singletonList(vf);
    }
    public List<ValidationFailure> getValidationFailures() {
        return ValidationFailures;
    }

    public void setValidationFailures(List<ValidationFailure> validationFailures) {
        ValidationFailures = validationFailures;
    }



}
