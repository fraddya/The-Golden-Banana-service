package com.fraddy.goldenbanana.domain.base;





import com.fraddy.goldenbanana.enums.RestApiResponseStatus;

import java.util.List;

public class ListResponseWrapper<T> extends BaseResponseWrapper {

    private List<T> content;

    public ListResponseWrapper(List<T> content) {
        super(RestApiResponseStatus.OK);
        this.content = content;
    }
    public ListResponseWrapper(List<T> content, String message) {
        super(RestApiResponseStatus.OK);
        this.content = content;
        this.message = message;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(final List<T> content) {
        this.content = content;
    }





}
