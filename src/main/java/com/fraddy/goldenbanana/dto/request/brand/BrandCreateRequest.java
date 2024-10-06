package com.fraddy.goldenbanana.dto.request.brand;


import lombok.Data;

@Data
public class BrandCreateRequest {
    private String name;
    private String code;
    private String description;
}
