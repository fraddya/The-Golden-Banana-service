package com.fraddy.goldenbanana.domain.base;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MapResponseWrapper<T> extends BaseResponseWrapper {

    private Map<String, List<T>> content;

    public MapResponseWrapper(Map<String, List<T>> content) {
        this.content = content;
    }

}
